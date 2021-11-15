import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequenceVerifier {
    public static boolean verifySequence(List<String> sequence, Automata automata) {
        if(!DeterministicChecker.isDeterministic(automata)){
            System.out.println("Automata is not deterministic.");
            return false;
        }
        if(!automata.alphabet.containsAll(sequence)){
            System.out.println("Automata does not contain all elements from sequence.");
            return false;
        }
        Map<Pair<String, String>, String> transitionsToFrom = new HashMap<>();
        for (var transition : automata.transitions) {
            transitionsToFrom.put(transition.getFirst(), transition.getSecond());
        }
        String currentState = automata.initialState;
        while(!automata.finalStates.contains(currentState) && sequence.size()!=0){
            System.out.println("Current state:"+currentState);
            String nextState =transitionsToFrom.get(new Pair<>(currentState, sequence.get(0)));
            if(nextState == null){
                System.out.println("No transition from this state to the next one through first element of sequence:"+sequence.get(0));
                return false;
            }
            currentState = nextState;
            sequence.remove(0);
        }
        System.out.println("Current state:"+currentState);
        if(automata.finalStates.contains(currentState)){
            System.out.println("Reached final state. Sequence accepted.");
            return true;
        }
        System.out.println("Final state not reached. Sequence not accepted.");
        return automata.finalStates.contains(currentState);
    }
}
