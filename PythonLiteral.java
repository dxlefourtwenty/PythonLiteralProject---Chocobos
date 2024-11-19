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

         // Define the NFA for Python floating-point, decimal, octal, and hexadecimal literals
        Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10", "q11"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.', 'e', 'E', '+', 'o', 'O', 'x', 'X',
            'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F', '_'
        ));

        // Define transitions
        Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();

        // Initial state
        transitions.put("q0", Map.of(
            Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q2"), // Start of decimal integer
            Set.of('0'), Set.of("q5"), // Starts with 0 (could be octal, hexadecimal, or decimal)
            Set.of('.'), Set.of("q3") // Starts with a decimal point
        ));

        // Decimal integer part
        transitions.put("q2", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q2"), // Continue decimal digits
            Set.of('_'), Set.of("q9"), // Underscore in decimal integer
            Set.of('.'), Set.of("q3"), // Transition to fractional part
            Set.of('e', 'E'), Set.of("q4") // Transition to exponent part
        ));

        // Handle underscore after decimal digits
        transitions.put("q9", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q2") // Digits after underscore
        ));

        // Floating point fractional part
        transitions.put("q3", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q3"), // Fractional digits
            Set.of('e', 'E'), Set.of("q4") // Transition to exponent part
        ));

        // Exponent part
        transitions.put("q4", Map.of(
            Set.of('+', '-'), Set.of("q6"), // Optional sign in exponent
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q6") // Digits in exponent
        ));
        transitions.put("q6", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q6") // Continue exponent digits
        ));

        // Octal and Hexadecimal prefixes
        transitions.put("q5", Map.of(
            Set.of('o', 'O'), Set.of("q7"), // Octal prefix
            Set.of('x', 'X'), Set.of("q8"), // Hexadecimal prefix
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q2"), // Decimal literal starting with 0
            Set.of('_'), Set.of("q9"), // Underscore after 0
            Set.of('.'), Set.of("q3") // Floating point starting with 0
        ));

        // Octal digits
        transitions.put("q7", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7'), Set.of("q7"), // Octal digits
            Set.of('_'), Set.of("q10") // Underscore in octal digits
        ));
        transitions.put("q10", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7'), Set.of("q7") // Continue after underscore in octal
        ));

        // Hexadecimal digits
        transitions.put("q8", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'), Set.of("q8"), // Hexadecimal digits
            Set.of('_'), Set.of("q11") // Underscore in hexadecimal digits
        ));
        transitions.put("q11", Map.of(
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'), Set.of("q8") // Continue after underscore in hexadecimal
        ));

        // Define the start state and accept states
        String startState = "q0";
        Set<String> acceptStates = new HashSet<>(Arrays.asList("q2", "q3", "q4", "q6", "q7", "q8", "q5"));

        // Create the NFA
        NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
 
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
