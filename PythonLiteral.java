import java.util.Set;
import java.util.HashSet;
import java.util.*;

public class PythonLiteral {

    public static void main(String[] args) {
        // Define the NFA
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));

        // Define the transitions
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();

        // q0 --a--> {q0, q1}
        transitions.put("q0", new HashMap<>());
        transitions.get("q0").put('a', new HashSet<>(Arrays.asList("q0", "q1")));

        // q1 --b--> {q2}
        transitions.put("q1", new HashMap<>());
        transitions.get("q1").put('b', new HashSet<>(Collections.singletonList("q2")));

        // q2 --epsilon--> {q0}
        transitions.put("q2", new HashMap<>());
        transitions.get("q2").put('b', new HashSet<>(Collections.singletonList("q0")));

        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Collections.singletonList("q2"));

        // Instantiate the NFA
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);

        
        System.out.println(nfa);
        System.out.println(nfa.accept("ab"));
        System.out.println(nfa.accept("aaab"));
        System.out.println(nfa.accept("ba"));
    }

}
