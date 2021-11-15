import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Automata automata = AutomataReader.readAutomata("faNonDeterministic.in");
        Menu.runMenu(automata);
    }
}
