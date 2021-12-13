import domain.Grammar;
import domain.Pair;
import domain.State;

import java.util.*;

public class Parser {

    List<Pair<String,List<String>>> productions = new ArrayList<>();
    Pair<String,List<String>> initialProduction;
    HashMap table;
    private Grammar grammar;
    private String newStartSymbol;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        String newStartSymbol = "S'";
        this.newStartSymbol = newStartSymbol;
        grammar.getNonTerminals().add(newStartSymbol);
        for (var prod: grammar.getProductions()){
                productions.add(new Pair<>(prod.first, prod.second));
        }
        List<String> initialList = new ArrayList<>();
        initialList.add(".");
        initialList.add(grammar.getStart());
        productions.add(new Pair<>(newStartSymbol, initialList ));
        initialProduction=(new Pair<>(newStartSymbol, initialList) );
    }


    public State closure(List<Pair<String, List<String>>> productions) {
        List<Pair<String,List<String>>> modifiable = new ArrayList<>();
        for(var prod: productions){
            modifiable.add(prod.of(prod));
        }
        boolean modified = true;
        while (modified) {
            modified=false;
            //keep this to avoid ConcurrentModificationException in for(var prod: modifiable)
            List<Pair<String,List<String>>> productionsToAdd = new ArrayList<>();

            for(var prod: modifiable) {
                int dotPos = prod.second.indexOf(".");
                String startOfProduction = dotPos!=-1 && !prod.second.get(prod.second.size()-1).equals(".")?prod.second.get(dotPos+1):null;
                if(hasDotAndDoesNotEndWithDot(prod, dotPos) &&
                        grammar.getNonTerminals().contains(startOfProduction)){
                            var prodsToModify =grammar.getProductionsForGivenNonterminal(startOfProduction);
                            for (var prodToModify: prodsToModify){
                                ArrayList<String> newProd = new ArrayList<>(prodToModify.second);
                                newProd.add(0, ".");
                                var crtPair = new Pair<String, List<String>>(startOfProduction, newProd);
                                if(!modifiable.contains(crtPair)){
                                    productionsToAdd.add(crtPair);
                                    modified=true;
                                }
                            }
                }
            }
            modifiable.addAll(productionsToAdd);
        }
        return new State(modifiable);
    }

    private boolean hasDotAndDoesNotEndWithDot(Pair<String, List<String>> prod, int dotPos) {
        return dotPos != -1 &&
                !prod.second.get(prod.second.size()-1).equals(".");
    }

    public State goTo(State state, String symbol) {
        //C->a.D
        //B->a.E
        //==> [closure(C->aD.), closure(B->aE.)]
        var productionsToCheck = new ArrayList<Pair<String, List<String>>>();
        for (var prod : state.productions) {
            int dotPos = prod.second.indexOf(".");
            if (hasDotAndDoesNotEndWithDot(prod, dotPos) &&
                    (prod.second.get(dotPos + 1)).equals(symbol)) {
                //S->.aB ==>  S->a.B
                List<String> newProd = new ArrayList<>(prod.second);
                newProd.add(dotPos+2, ".");
                newProd.remove(".");
                Pair<String, List<String>> newProdd = new Pair<>(prod.first, newProd);
                productionsToCheck.add(newProdd);
            }
        }
        if (!productionsToCheck.isEmpty()) {
            return closure(productionsToCheck);
        }
        return new State(Collections.emptyList());
    }

    public List<State> canonicalCollection() {

        List<State> states = new ArrayList<>();
        var firstElem = closure(List.of(initialProduction));
        states.add(firstElem);

        boolean colCanModified = true;
        var allSymbols = new ArrayList<>(grammar.getNonTerminals());
        allSymbols.addAll(grammar.getTerminals());
        while (colCanModified) {
            //keep this to avoid ConcurrentModificationException in for(State state:states)
            List<State> statesToAdd = new ArrayList<>();
            colCanModified = false;
            for (State state : states) {
                for (var symbol : allSymbols) {
                    var newState = goTo(state, symbol);
                    if (!newState.productions.isEmpty()) {
                        if (!states.contains(newState)) {
                            colCanModified=true;
                            statesToAdd.add(newState);
                        }
                    }
                }
            }
            states.addAll(statesToAdd);
        }
        return states;

    }

}
