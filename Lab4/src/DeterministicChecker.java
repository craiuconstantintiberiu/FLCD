import java.util.HashMap;
import java.util.Map;

public class DeterministicChecker {

    public static boolean isDeterministic(Automata automata) {
        Map<Pair<String, String>, String> transitionsToFrom = new HashMap<>();
        for (var transition : automata.transitions) {
            if (transitionsToFrom.containsKey(transition.getFirst())) {
                var existingValue = transitionsToFrom.get(transition.getFirst());
                System.out.println("FA is not deterministic because there are these 2 conflicting transitions:" +
                        "(" + transition.getFirst().getFirst() + ", " + transition.getFirst().getSecond() + ")" + "->" + transition.getSecond() + " and "
                        + "(" + transition.getFirst().getFirst() + ", " + transition.getFirst().getSecond() + ")" + "->" + existingValue);
                return false;
            }
            transitionsToFrom.put(transition.getFirst(), transition.getSecond());
        }
        System.out.println("Is deterministic.");
        return true;
    }
}
