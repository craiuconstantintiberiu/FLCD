import domain.Grammar;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Grammar grammar = Grammar.readFromFile("g1.txt");
        grammar.printProductions();
        grammar.printNonTerminals();
        grammar.printTerminals();
        grammar.printProductionsForGivenNonterminal("A");

        Grammar.readFromFile("g1NonTerminal.txt");
    }
}
