import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AutomataReader {

    public static Automata readAutomata(String file) throws IOException {
        Automata automata = new Automata();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine;
        //Read alphabet:
        currentLine = reader.readLine();
        var elements = currentLine.split(",");
        System.out.println(Arrays.toString(elements));
        automata.alphabet.addAll(List.of(elements));

        //Read states:
        currentLine = reader.readLine();
        elements = currentLine.split(",");
        System.out.println(Arrays.toString(elements));
        automata.states.addAll(List.of(elements));

        //Read final states:
        currentLine = reader.readLine();
        elements = currentLine.split(",");
        System.out.println(Arrays.toString(elements));
        automata.finalStates.addAll(List.of(elements));

        //Read transitions:
        currentLine = reader.readLine();
        elements = currentLine.split(",");
        System.out.println(Arrays.toString(elements));
        var transitions = Arrays.stream(elements).map(elems -> elems.split(" ")).collect(Collectors.toList());
        for (String[] transition : transitions) {
            automata.transitions.add(new Pair<>(new Pair<>(transition[0], transition[1]), transition[2]));
        }
        return automata;
    }
}
