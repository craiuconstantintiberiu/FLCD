import java.util.Scanner;

public class Menu {

    public static void runMenu(Automata automata) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Print alphabet");
            System.out.println("2. Print initial state");
            System.out.println("3. Print states");
            System.out.println("4. Print final states");
            System.out.println("5. Print transitions");
            System.out.println("6. Check if deterministic");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> printAlphabet(automata);
                case "2" -> printInitialState(automata);
                case "3" -> printStates(automata);
                case "4" -> printFinalStates(automata);
                case "5" -> printTransitions(automata);
                case "6" -> DeterministicChecker.isDeterministic(automata);
                default -> System.out.println("Choose again.");
            }
        }
    }

    private static void printInitialState(Automata automata) {
        System.out.println("Initial state:");
        System.out.println(automata.initialState);
    }

    private static void printFinalStates(Automata automata) {
        System.out.println("Final States:");
        StringBuilder builder = new StringBuilder();
        for (var finalState : automata.finalStates) {
            builder.append(finalState).append(" ");
        }
        System.out.println(builder);
    }

    private static void printTransitions(Automata automata) {
        System.out.println("Transitions:");
        StringBuilder builder = new StringBuilder();
        for (var transition : automata.transitions) {
            builder.append("(")
                    .append(transition.getFirst().getFirst())
                    .append(',')
                    .append(transition.getFirst().getSecond())
                    .append(')')
                    .append("->")
                    .append(transition.getSecond())
                    .append(" ");
        }
        System.out.println(builder);
    }

    private static void printStates(Automata automata) {
        System.out.println("States:");
        StringBuilder builder = new StringBuilder();
        for (var states : automata.states) {
            builder.append(states).append(" ");
        }
        System.out.println(builder);
    }

    private static void printAlphabet(Automata automata) {
        System.out.println("Alphabet:");
        StringBuilder builder = new StringBuilder();
        for (var letter : automata.alphabet) {
            builder.append(letter).append(" ");
        }
        System.out.println(builder);
    }
}
