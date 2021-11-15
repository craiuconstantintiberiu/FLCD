import java.util.ArrayList;
import java.util.List;

public class Automata {
    public List<String> alphabet;
    public List<Pair<Pair<String, String>, String>> transitions;
    public List<String> states;
    public String initialState;
    public List<String> finalStates;

    public Automata() {
        alphabet = new ArrayList<>();
        transitions = new ArrayList<>();
        states = new ArrayList<>();
        finalStates = new ArrayList<>();
    }
}
