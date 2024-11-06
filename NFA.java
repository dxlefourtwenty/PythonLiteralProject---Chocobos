import java.util.HashSet;
import java.util.Set;
import java.util.*;

public class NFA {

    private Set<Integer> states;
    private Set<Character> alphabet;
    private int startState;
    private Set<Integer> acceptStates;
    private Set<Transition> transitions;
    private int finalState;
    private Set<Integer> finalStates;

    public NFA(Set<Integer> states, Set<Character> alphabet, int startState, int finalState, Set<Integer> acceptStates, Set<Transition> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.transitions = transitions;
        this.finalState = finalState;
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

    public static class Transition {
        int fromState;
        int toState;
        char symbol;

        Transition(int fromState, int toState, char symbol) {
            this.fromState = fromState;
            this.toState = toState;
            this.symbol = symbol;
        }
    }

    public void addTransition(int fromState, int toState, char symbol) {
        transitions.add(new Transition(fromState, toState, symbol));
    }

    public String getTransitions() {
        StringBuilder result = new StringBuilder();
        for (Transition transition : transitions) {
            result.append(transition.fromState)
                  .append(" -> ")
                  .append(transition.toState)
                  .append(" on ")
                  .append(transition.symbol)
                  .append("\n");
        }
        return result.toString();
    }

    public void addState(int state) {
        states.add(state);
    }

    public void addAcceptState(int state) {
        acceptStates.add(state);
    }

    public void setStartState(int state) {
        startState = state;
    }

    public void setFinalState(int state) {
        finalState = state;
    }

    public int getStartState() {
        return startState;
    }

    public Set<Integer> getFinalStates() {
        return new HashSet<Integer>(finalStates);
    }

    public Set<Integer> getStates() {
        return new HashSet<Integer>(states);
    }

}
