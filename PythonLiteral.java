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

        // Define the NFA for Python floating-point literals
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3", "q4", "q5"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.', 'e', 'E', '+'));

        // Define transitions
        Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();

        // Initial state
        transitions.put("q0", Map.of(
            Set.of('-'), Set.of("q2"), // Optional leading negative sign
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"), // Integer part
            Set.of('.'), Set.of("q5") // Starts with a decimal point (fractional part only)
        ));

        // Integer part
        transitions.put("q1", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"), // Continue digits
            Set.of('.'), Set.of("q5"), // Transition to fractional part
            Set.of('e', 'E'), Set.of("q3") // Transition to exponent part
        ));

        // Negative sign handling
        transitions.put("q2", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"), // Integer part after negative sign
            Set.of('.'), Set.of("q5") // Decimal point after negative sign
        ));

        // Fractional part
        transitions.put("q5", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q5"), // Fractional digits
            Set.of('e', 'E'), Set.of("q3") // Transition to exponent part
        ));

        // Exponent part
        transitions.put("q3", Map.of(
            Set.of('+', '-'), Set.of("q4"), // Optional sign in exponent
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q4") // Digits in exponent
        ));
        transitions.put("q4", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q4") // Continue exponent digits
        ));

        // Define the start state and accept states
        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Arrays.asList("q1", "q5", "q4"));

        // Create the NFA
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);

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
