import java.util.HashSet;
import java.util.Set;

public class NFA {

    private Set<Integer> states;
    private Set<Character> alphabet;
    private int startState;
    private Set<Integer> acceptStates;
    private Set<Transition> transitions;

    public NFA(Set<Integer> states, Set<Character> alphabet, int startState, Set<Integer> acceptStates, Set<Transition> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.transitions = transitions;
    }

    public boolean accepts(String input) {
        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(startState);
        for (char symbol : input.toCharArray()) {
            currentStates = getNextStates(currentStates, symbol);
        }
        for (int state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    private Set<Integer> getNextStates(Set<Integer> currentStates, char symbol) {
        Set<Integer> nextStates = new HashSet<>();
        for (int state : currentStates) {
            for (Transition transition : transitions) {
                if (transition.fromState == state && transition.symbol == symbol) {
                    nextStates.add(transition.toState);
                }
            }
        }
        return nextStates;
    }

    private static class Transition {
        int fromState;
        int toState;
        char symbol;

        Transition(int fromState, int toState, char symbol) {
            this.fromState = fromState;
            this.toState = toState;
            this.symbol = symbol;
        }
    }

}
