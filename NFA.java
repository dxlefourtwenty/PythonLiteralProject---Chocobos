import java.util.*;

public class NFA {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Character, Set<String>>> transitions;
    private String startState;
    private Set<String> acceptStates;

    // Constructor
    public NFA(Set<String> states, Set<Character> alphabet, Map<String, Map<Character, Set<String>>> transitions,
               String startState, Set<String> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.acceptStates = acceptStates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("States: ").append(states).append("\n");
        sb.append("Alphabet: ").append(alphabet).append("\n");
        sb.append("Start State: ").append(startState).append("\n");
        sb.append("Accept States: ").append(acceptStates).append("\n");
        sb.append("Transitions:\n");

        for (String state : transitions.keySet()) {
            for (Character symbol : transitions.get(state).keySet()) {
                Set<String> nextStates = transitions.get(state).get(symbol);
                String symbolStr = (symbol == null) ? "ε" : symbol.toString();  // ε represents epsilon
                sb.append("  ").append(state).append(" --").append(symbolStr)
                  .append("--> ").append(nextStates).append("\n");
            }
        }
    
        return sb.toString();
    }

    // Main accept method to check if the input string is accepted
    public boolean accept(String input) {
        return acceptHelper(startState, input, 0);
    }

    // Recursive helper method
    private boolean acceptHelper(String currentState, String input, int index) {
        // Base case: if we've reached the end of the input, check if we are in an accept state
        if (index == input.length()) {
            return acceptStates.contains(currentState);
        }

        // Get the current symbol in the input
        char symbol = input.charAt(index);

        // Check transitions for the current symbol
        if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(symbol)) {
            for (String nextState : transitions.get(currentState).get(symbol)) {
                // Recursively check the remaining input from the next state
                if (acceptHelper(nextState, input, index + 1)) {
                    return true;
                }
            }
        }

        // Check epsilon (empty string) transitions
        if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(null)) {
            for (String nextState : transitions.get(currentState).get(null)) {
                // Recursively check from the next state without consuming input
                if (acceptHelper(nextState, input, index)) {
                    return true;
                }
            }
        }

        // If no paths lead to acceptance, return false
        return false;
    }
}