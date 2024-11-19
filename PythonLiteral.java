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

         // Define the NFA for negative, nonzero decimal, octal, and hexadecimal integers
         Set<String> states = new HashSet<>(Arrays.asList("q0", "q1", "q2", "q3"));
         Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F', 'x', 'X'));
 
         // Define transitions
         Map<String, Map<Set<Character>, Set<String>>> transitions = new HashMap<>();
    transitions.put("q0", Map.of(
        Set.of('1'), Set.of("q2"),                             // From start state, '-' goes to q2 for negative integers
        Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"), // Positive numbers start at q1
        Set.of('0'), Set.of()                                  // 0 not be accepted by itself
    ));
    transitions.put("q2", Map.of(
        Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"), // Negative numbers start at q1 after '-'
        Set.of('0'), Set.of("q1", "q3")                            // Negative octal/hexadecimal
    ));
    transitions.put("q1", Map.of(
        Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), Set.of("q1"),   // Loop on decimal numbers
        Set.of('0', '1', '2', '3', '4', '5', '6', '7'), Set.of("q1")              // Loop on octal digits
    ));
    transitions.put("q3", Map.of(
        Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'),
        Set.of("q3") // Loop on hexadecimal digits
    ));
 
         // Define the start state and accept states
         String startState = "q0";
         Set<String> acceptStates = new HashSet<>(Arrays.asList("q1", "q3"));
 
         // Create the NFA
         NFA nfa = new NFA(states, alphabet, transitions, startState, acceptStates);
 
         // Print the NFA and test inputs
         //System.out.println(nfa);
         /*System.out.println("Accept '123': " + nfa.accept("123"));       // true (decimal)
         System.out.println("Accept '-123': " + nfa.accept("-123"));     // true (negative decimal)
         System.out.println("Accept '074': " + nfa.accept("074"));       // true (octal)
         System.out.println("Accept '0x3F': " + nfa.accept("0x3F"));     // true (hexadecimal)
         System.out.println("Accept '0Xf5': " + nfa.accept("0Xf5"));     // true (hexadecimal)
         System.out.println("Accept '-0x3F': " + nfa.accept("-0x3F"));   // true (negative hexadecimal)
         System.out.println("Accept '0': " + nfa.accept("0"));           // false (not a valid nonzero integer)
         System.out.println("Accept '00': " + nfa.accept("00"));         // false (not valid)
         System.out.println("Accept '0x': " + nfa.accept("0x"));         // false (invalid hexadecimal)*/
 


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
