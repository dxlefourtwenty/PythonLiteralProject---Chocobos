import java.util.*;

public class PythonLiteral {

    public static void main(String[] args) {
        // Define the NFA
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));

        // Define the transitions
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();

        // q0 --a--> {q0, q1}
        Map<Character, Set<String>> q0Transitions = new HashMap<>();
        q0Transitions.put('a', new LinkedHashSet<>(Arrays.asList("q0", "q1")));
        transitions.put("q0", q0Transitions);

        // q1 --b--> {q2}
        Map<Character, Set<String>> q1Transitions = new HashMap<>();
        q1Transitions.put('b', new LinkedHashSet<>(Collections.singletonList("q2")));
        transitions.put("q1", q1Transitions);

        // q2 --epsilon--> {q0}
        Map<Character, Set<String>> q2Transitions = new HashMap<>();
        q2Transitions.put(null, new LinkedHashSet<>(Collections.singletonList("q0")));
        transitions.put("q2", q2Transitions);

        String startState = "q0";
        Set<String> acceptStates = new LinkedHashSet<>(Collections.singletonList("q2"));

        // Instantiate the NFA
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);

        
        System.out.println(nfa);
        System.out.println("Accepts 'ab' >> " + nfa.accept("ab"));
        System.out.println("Accepts 'aaab' >> " + nfa.accept("aaab"));
        System.out.println("Accepts 'ba' >> " + nfa.accept("ba"));
    }

}
