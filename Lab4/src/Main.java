import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Automata automata = AutomataReader.readAutomata("fa.in");
        ArrayList<String> sequenceToVerify = new ArrayList<>(List.of("a", "b"));
        System.out.println("Is sequence accepted by DFA?" + SequenceVerifier.verifySequence(sequenceToVerify,automata));
        Menu.runMenu(automata);
    }
}
