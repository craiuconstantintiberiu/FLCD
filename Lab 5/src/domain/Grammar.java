package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// la parsare trebuie CFG
public class Grammar {

    private List<String> nonTerminals;
    private List<String> terminals;
    private List<Pair<String, List<String>>> productions;
    private String start;

    public Grammar() {
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.productions = new ArrayList<>();
    }

    public static Grammar readFromFile(String filename) throws IOException {
        Grammar grammar = new Grammar();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String currentLine;

        //Read nonterminals:
        currentLine = reader.readLine();
        var elements = currentLine.split(",");
        grammar.getNonTerminals().addAll(List.of(elements));

        //Read terminals:
        currentLine = reader.readLine();
        elements = currentLine.split(",");
        grammar.getTerminals().addAll(List.of(elements));

        //Read productions
        currentLine = reader.readLine();
        elements = currentLine.split(",");
        var productions = Arrays.stream(elements).map(elems -> elems.split(" ")).collect(Collectors.toList());
        for (String[] production : productions) {
            var firstElemProduction = production[0];
            List<String> productionElems = new ArrayList<>();
            IntStream.range(1, production.length).forEach(index -> productionElems.add(production[index]));
            grammar.getProductions().add(new Pair<>(firstElemProduction, productionElems));
        }

        //Read start:
        currentLine = reader.readLine();
        grammar.setStart(currentLine);

        if (!grammar.isCFG()) {
            System.out.println("Not CFG.");
            throw new IllegalArgumentException();
        }
        return grammar;
    }

    public boolean isCFG() {
        return productions.stream().allMatch(prod -> nonTerminals.contains(prod.getFirst()) && prod.getFirst().length() == 1);
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(List<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }

    public List<Pair<String, List<String>>> getProductions() {
        return productions;
    }

    public void setProductions(List<Pair<String, List<String>>> productions) {
        this.productions = productions;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void printNonTerminals() {
        System.out.println("Nonterminals:");
        System.out.println(nonTerminals);
    }

    public void printTerminals() {
        System.out.println("Terminals");
        System.out.println(terminals);
    }

    public void printProductions() {
        System.out.println("Productions");
        StringBuilder builder = new StringBuilder();
        for (var production : productions) {
            builder.append(production.getFirst())
                    .append("->")
                    .append(String.join("|", production.getSecond()))
                    .append("\n");
        }
        System.out.println(builder);
    }

    public void printProductionsForGivenNonterminal(String nonterminal) {
        System.out.println("Productions for nonterminal:" + nonterminal);
        var productionsMatching = productions.stream().filter(prod -> prod.getFirst().equals(nonterminal)).collect(Collectors.toList());
        if (productionsMatching.isEmpty()) {
            System.out.println("No matching nonterminal");
            return;
        }
        var production = productionsMatching.get(0);

        String builder = production.getFirst() +
                "->" +
                String.join("|", production.getSecond()) +
                " ";
        System.out.println(builder);
    }

}
