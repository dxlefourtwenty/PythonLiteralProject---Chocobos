import java.util.*;
import java.io.*;
import java.nio.file.*;

public class PythonLiteral {

    public static void main(String[] args) {
        //testNFA();
        PythonLiteralNFA();
    }

    public static void testNFA() {

        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c'));

        Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();
        transitions.put("q0", Map.of(Set.of('a', 'b'), Set.of("q1"))); // q0 -> q1
        transitions.put("q1", Map.of(Set.of('b'), Set.of("q2"))); // q1 -> q2
        transitions.put("q2", Map.of(Set.of('c'), Set.of("q3"))); // q2 -> q3cb

        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Arrays.asList("q3"));

        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);

        System.out.println(nfa);
        System.out.println("Accept 'ac': " + nfa.accept("ac")); // true
        System.out.println("Accept 'bc': " + nfa.accept("bc")); // true
        System.out.println("Accept 'cc': " + nfa.accept("cc")); // false

        System.out.println("Enter a string to check if it is accepted by the NFA: ");
        Scanner scnr = new Scanner(System.in);
        String userInput = scnr.nextLine();
        System.out.println("Accepts '" + userInput + "' >> " + nfa.accept(userInput));
        scnr.close();
    }

    public static void PythonLiteralNFA() {

        // Define the NFA for negative and nonzero integers
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'));

        // Define transitions
        Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();
        transitions.put("q0", Map.of(
            Set.of('-'), Set.of("q2"),     // From start state, '-' goes to q2 for negative integers
            Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1") // Positive numbers start at q1
        ));
        transitions.put("q2", Map.of(
            Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1") // Negative numbers start at q1 after '-'
        ));
        transitions.put("q1", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1") // Loop on digits in q1
        ));

        // Define the start state and accept states
        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Arrays.asList("q1"));

        // Create the NFA
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);

        // Print the NFA and test inputs
        System.out.println(nfa);
        System.out.println("Accept '123': " + nfa.accept("123"));   // true
        System.out.println("Accept '-123': " + nfa.accept("-123")); // true
        System.out.println("Accept '0': " + nfa.accept("0"));       // false
        System.out.println("Accept '4560': " + nfa.accept("4560")); // true
        System.out.println("Accept '-4560': " + nfa.accept("-4560"));// true
        System.out.println("Accept '-0': " + nfa.accept("-0"));     // false
        System.out.println("Accept '00': " + nfa.accept("00"));     // false
        /* 
        // user input method
        System.out.println("Enter a string to check if it is accepted by the NFA: "); 
        Scanner scnr = new Scanner(System.in);
        String userInput = scnr.nextLine();
        System.out.println("Accepts '" + userInput + "' >> " + nfa.accept(userInput));
        scnr.close();
        */

        // read from file method
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter the name of the file to read: ");
        String fileName = scnr.nextLine();
        String outputFile = "output.txt";
        try {
            List<String> lines = ReadInputFile(fileName);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
            for (String line : lines) {
                fileWriter.write("Accepts '" + line + "' >> " + nfa.accept(line) + "\n");
            }
            System.out.println("Output written to " + outputFile);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scnr.close();
    } 

    public static List<String> ReadInputFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }
}
