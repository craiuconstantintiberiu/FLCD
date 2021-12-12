import domain.Grammar;
import domain.Pair;
import domain.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Grammar grammar = Grammar.readFromFile("g1.txt");
       /* grammar.printProductions();
        grammar.printNonTerminals();
        grammar.printTerminals();
        grammar.printProductionsForGivenNonterminal("A");*/

        Parser parser =new Parser(grammar);
        System.out.println(parser.canonicalCollection());
        System.out.println(parser.closure(List.of(parser.initialProduction)));
        System.out.println(parser.goTo(new State
                (List.of(new Pair<>("S'",".S"), new Pair<>("S",".aA"), new Pair<>("S",".ac")))
                ,"a"));
    }
}
