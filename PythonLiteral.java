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

         // Defining NFA for python literals (nonzero decimal, octal, and hexadecimal integers)
         Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3", "q4"));
         Set<Character> alphabet = new HashSet<>(Arrays.asList( '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F', 'x', 'X'));
 
         // Define transitions
         Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();
 
         // q0: Start state
         transitions.put("q0", Map.of(
             Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'), Set.of("q1"), // Positive decimal numbers start at q1
             Set.of('0'), Set.of("q2")  // 0 goes to q2 (check for octal/hexadecimal)
         ));
 
         // q1: Positive decimal or octal numbers
         transitions.put("q1", Map.of(
             Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"),  // Loop on decimal digits
             Set.of('_'), Set.of("q1")  // Loop on underscores (underscore between digits)
         ));
 
         // q2: Handles the '0' for octal or hexadecimal prefix
         transitions.put("q2", Map.of(
             Set.of('x', 'X'), Set.of("q3"), // Transition to hexadecimal if 'x' or 'X'
             Set.of('0', '1', '2', '3', '4', '5', '6', '7'), Set.of("q1"),  // Transition to octal if valid octal digit
             Set.of('_'), Set.of()  // Invalid to start with an underscore
         ));
 
         // q3: Hexadecimal number processing (after '0x' or '0X')
         transitions.put("q3", Map.of(
             Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F', '_'),
             Set.of("q4") // Transition to q4 for hexadecimal digits
         ));
 
         // q4: Hexadecimal number processing (hexadecimal digits loop)
         transitions.put("q4", Map.of(
             Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F', '_'),
             Set.of("q4") // Loop on hexadecimal digits and underscores
         ));
 
         // Define the start state and accept states
         String startState = "q0";
         Set<String> acceptStates = new HashSet<>(Arrays.asList("q1", "q4"));  // Accept q1 for decimal and q4 for hexadecimal
 
         // Create the NFA
         NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
  

        /*
        // Print the NFA and test inputs
        System.out.println(nfa);
        System.out.println("Accept '123': " + nfa.accept("123"));   // true
        System.out.println("Accept '-123': " + nfa.accept("-123")); // true
        System.out.println("Accept '0': " + nfa.accept("0"));       // false
        System.out.println("Accept '4560': " + nfa.accept("4560")); // true
        System.out.println("Accept '-4560': " + nfa.accept("-4560"));// true
        System.out.println("Accept '-0': " + nfa.accept("-0"));     // false
        System.out.println("Accept '00': " + nfa.accept("00"));     // false
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
