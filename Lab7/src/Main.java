import domain.Grammar;
import domain.Pair;
import domain.State;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Grammar grammar = Grammar.readFromFile("g1.txt");
        grammar.printProductions();
        grammar.printNonTerminals();
        grammar.printTerminals();
        grammar.printProductionsForGivenNonterminal("A");

        Parser parser =new Parser(grammar);
        System.out.println(parser.canonicalCollection());
        var x = parser.canonicalCollection();
        int id =0;
        for (State state: x){
            System.out.println("I"+id++);
            printState(state);
            System.out.println();
        }
        System.out.println(parser.closure(List.of(parser.initialProduction)));
        System.out.println(parser.goTo(new State
                (List.of(new Pair<>("S'",List.of(".","S")), new Pair<>("S",List.of(".","a","A")), new Pair<>("S",List.of(".","a","c")))),"a"));
    }

    private static void printState(State state){
        for(var prod: state.productions){
            System.out.println(prod.first+"->"+ String.join("", prod.second));
        }
    }
}
