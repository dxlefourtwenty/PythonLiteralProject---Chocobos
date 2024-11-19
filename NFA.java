import java.util.*;
//hello
public class NFA {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Set<Character>, Set<String>>> transitions;
    private String startState;
    private Set<String> acceptStates;

    // Constructor
    public NFA(Set<String> states, Set<Character> alphabet, Map<String, Map<Set<Character>, Set<String>>> transitions, String startState, Set<String> acceptStates) {
        this.states = new LinkedHashSet<>(states);
        this.alphabet = new LinkedHashSet<>(alphabet);
        this.transitions = new LinkedHashMap<>();

        for (String state : transitions.keySet()) {
            Map<Set<Character>, Set<String>> stateTransitions = new LinkedHashMap<>();
            for (Set<Character> symbols : transitions.get(state).keySet()) {
                stateTransitions.put(new LinkedHashSet<>(symbols), new LinkedHashSet<>(transitions.get(state).get(symbols)));
            }
            this.transitions.put(state, stateTransitions);
        }

        this.startState = startState;
        this.acceptStates = new LinkedHashSet<>(acceptStates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> sortedStates = new ArrayList<>(states);
        Collections.sort(sortedStates);
        sb.append("States: ").append(sortedStates).append("\n");
        sb.append("Alphabet: ").append(alphabet).append("\n");
        sb.append("Start State: ").append(startState).append("\n");
        sb.append("Accept States: ").append(acceptStates).append("\n");
        sb.append("Transitions:\n");

        List<String> sortedTransitions = new ArrayList<>(transitions.keySet());
        Collections.sort(sortedTransitions);

        for (String state : sortedTransitions) {
            List<Character> sortedSymbols = new ArrayList<>();
            for (Set<Character> symbolSet : transitions.get(state).keySet()) {
                sortedSymbols.addAll(symbolSet);
            }
            Collections.sort(sortedSymbols, Comparator.nullsFirst(Comparator.naturalOrder()));

            for (Character symbol : sortedSymbols) {
                Set<String> nextStates = transitions.get(state).get(Collections.singleton(symbol));
                String symbolStr = (symbol == null) ? "ε" : symbol.toString();  // ε represents epsilon
                sb.append("  ").append(state).append(" --").append(symbolStr)
                  .append("--> ").append(nextStates).append("\n");
            }
        }
    
        return sb.toString();
    }

    // Main accept method to check if the input string is accepted
    public boolean accept(String input) {
        // Stack-based simulation of the NFA
        Deque<Pair<String, Integer>> stack = new ArrayDeque<>();
        Set<Pair<String, Integer>> visited = new HashSet<>();
    
        // Initialize the stack with the start state and input index 0
        stack.push(new Pair<>(startState, 0));
    
        while (!stack.isEmpty()) {
            Pair<String, Integer> current = stack.pop();
            String currentState = current.first;
            int index = current.second;
    
            // If we've reached the end of the input, check if we are in an accept state
            if (index == input.length() && acceptStates.contains(currentState)) {
                return true;
            }
    
            // Avoid re-processing the same state and input index
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
    
            // Get the current input symbol, if any
            Character symbol = (index < input.length()) ? input.charAt(index) : null;
    
            // Process transitions for the current state
            if (transitions.containsKey(currentState)) {
                Map<Set<Character>, Set<String>> stateTransitions = transitions.get(currentState);
    
                for (Set<Character> symbols : stateTransitions.keySet()) {
                    // If the symbol is part of the current set, follow the transitions
                    if (symbol != null && symbols.contains(symbol)) {
                        for (String nextState : stateTransitions.get(symbols)) {
                            stack.push(new Pair<>(nextState, index + 1));
                        }
                    }
    
                    // Add epsilon transitions
                    if (symbols.contains(null)) {
                        for (String nextState : stateTransitions.get(symbols)) {
                            stack.push(new Pair<>(nextState, index));
                        }
                    }
                }
            }
        }
    
        // If we exhaust the stack without reaching an accept state, reject the input
        return false;
    }
}