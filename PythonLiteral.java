import java.util.Set;
import java.util.HashSet;

public class PythonLiteral {

    public static void main(String[] args) {
        Set<Integer> states = new HashSet<>();
        states.add(0);
        states.add(1);
        states.add(2);
        Set<Character> alphabet = new HashSet<>();
        alphabet.add('a');
        alphabet.add('b');
        Set<Integer> acceptStates = new HashSet<>();
        acceptStates.add(2);
        Set<NFA.Transition> transitions = new HashSet<>();
        transitions.add(new NFA.Transition(0, 1, 'a'));
        transitions.add(new NFA.Transition(1, 2, 'b')); 
        int startState = 0;
        int finalState = 2;
        NFA nfa = new NFA(states, alphabet, startState, finalState, acceptStates, transitions);
        nfa.addState(0);
        nfa.addState(1);
        nfa.addState(2);
        nfa.setStartState(0);
        nfa.setFinalState(2);
        nfa.addTransition(0, 1, 'a');
        nfa.addTransition(1, 2, 'b');

        System.out.println("NFA created with states: " + nfa.getStates());
        System.out.println("Start state: " + nfa.getStartState());
        System.out.println("Final states: " + nfa.getFinalStates());
        System.out.println("Transitions: " + nfa.getTransitions());
    }

}
