import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Grammar grammar = Grammar.readFromFile("gtest.txt");
        grammar.printProductions();
        grammar.printNonTerminals();
        grammar.printTerminals();
        grammar.printProductionsForGivenNonterminal("A");

        Parser parser =new Parser(grammar);
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

        System.out.println("\n\n---Parser Output part --- \n\n");


        ParserOutput parserOutput = new ParserOutput(parser);
        System.out.println("0."+parser.initialProduction.first+"->"+String.join("", parser.initialProduction.second));
        int index = 1;
        for(var prod: grammar.getProductions()){
            System.out.println(index+++"."+prod.first+"->"+ String.join("", prod.second));
        }

        System.out.println(parserOutput.verifySequence(List.of("a","b","b","c")));
        System.out.println(parserOutput.phi);
    }

    private static void printState(State state){
        for(var prod: state.productions){
            System.out.println(prod.first+"->"+ String.join("", prod.second));
        }
    }
}
