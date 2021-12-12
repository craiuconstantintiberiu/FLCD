import domain.Grammar;
import domain.Pair;
import domain.State;

import java.util.*;

public class Parser {

    List<Pair<String,String>> productions = new ArrayList<>();
    Pair<String,String> initialProduction;
    HashMap table;
    private Grammar grammar;
    private String newStartSymbol;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        String newStartSymbol = "S'";
        this.newStartSymbol = newStartSymbol;
        grammar.getNonTerminals().add(newStartSymbol);
        for (var prod: grammar.getProductions()){
            for(var to: prod.second){
                productions.add(new Pair<>(prod.first, to));
            }
        }
        productions.add(new Pair<>(newStartSymbol, "."+grammar.getStart()) );
        initialProduction=(new Pair<>(newStartSymbol, "."+grammar.getStart()) );
    }


    public State closure(List<Pair<String, String>> productions) {
        List<Pair<String,String>> modifiable = new ArrayList<>();
        for(var prod: productions){
            modifiable.add(prod.of(prod));
        }
        boolean modified = true;
        while (modified) {
            modified=false;
            //keep this to avoid ConcurrentModificationException in for(var prod: modifiable)
            List<Pair<String,String>> productionsToAdd = new ArrayList<>();

            for(var prod: modifiable) {
                int dotPos = prod.second.indexOf(".");
                String startOfProduction = dotPos!=-1 && !prod.second.endsWith(".")?String.valueOf(prod.second.charAt(dotPos+1)):null;
                if(hasDotAndDoesNotEndWithDot(prod, dotPos) &&
                        grammar.getNonTerminals().contains(startOfProduction)){
                            var prodsToModify =grammar.getProductionsForGivenNonterminal(startOfProduction);
                            for (var prodToModify: prodsToModify){
                                StringBuilder builder = new StringBuilder(prodToModify);
                                builder.insert(0,'.');
                                var crtPair = new Pair<String, String>(startOfProduction, builder.toString());
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

    private boolean hasDotAndDoesNotEndWithDot(Pair<String, String> prod, int dotPos) {
        return dotPos != -1 &&
                !prod.second.endsWith(".");
    }

    public State goTo(State state, String symbol) {
        //C->a.D
        //B->a.E
        //==> [closure(C->aD.), closure(B->aE.)]
        var productionsToCheck = new ArrayList<Pair<String, String>>();
        for (var prod : state.productions) {
            int dotPos = prod.second.indexOf(".");
            if (hasDotAndDoesNotEndWithDot(prod, dotPos) &&
                    String.valueOf(prod.second.charAt(dotPos + 1)).equals(symbol)) {
                //S->.aB ==>  S->a.B
                StringBuilder builder = new StringBuilder(prod.second);
                builder.insert(dotPos+2,'.');
                builder.delete(dotPos,dotPos+1);
                Pair<String, String> newProd = new Pair<>(prod.first, builder.toString());
                productionsToCheck.add(newProd);
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
