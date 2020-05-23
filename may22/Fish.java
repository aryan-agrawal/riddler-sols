package may22;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * Main class for the solution to The Riddler Classic Puzzle, from May 23rd 2020.
 *
 * @author Aryan Agrawal
 */
public class Fish {
    /**
     * First, reads all words and states into Collections from respective files. Sorts the
     * collection of words in descending order of length, and then iterates through the
     * sorted list to find the longest word that is a "mackerel" of exactly one US state.
     * A "mackerel" is defined as a word that shares no characters with a certain US state.
     * This method prints the longest mackerel, printing multiple if there are more than one
     * mackerel of that maximum length.
     * The method then iterates through all the words, and keeps track of the total number of
     * mackerels for each individual US state. It then prints out the state with the most
     * mackerels.
     */
    public static void main(String[] args) throws FileNotFoundException {
        File wordFile = new File("may22/words.txt");
        File stateFile = new File("may22/states.txt");
        Scanner wordScan = new Scanner(wordFile);
        Scanner stateScan = new Scanner(stateFile);
        String[] states = new String[50];
        for (int index = 0; index < 50; index++) {
            states[index] = stateScan.nextLine();
        }
        ArrayList<String> words = new ArrayList<>();
        while (wordScan.hasNextLine()) {
            words.add(wordScan.nextLine());
        }
        words.sort((String x, String y) ->  Integer.compare( y.length(), x.length()));
        int maxLength = 0;
        boolean maxFound = false;
        for (String word : words) {
            if (maxFound && word.length() < maxLength) {
                break;
            }
            int numStates = 0;
            for (String state : states) {
                if (disjoint(word.toCharArray(), state.toCharArray())) {
                    numStates++;
                    if (numStates > 1) {
                        break;
                    }
                }
            }
            if (numStates == 1) {
                System.out.println(word);
                if (!maxFound) {
                    maxLength = word.length();
                    maxFound = true;
                }
            }
        }
        HashMap<String, Integer> stateMacks = new HashMap<>();
        for (String state : states) {
            stateMacks.put(state, 0);
        }
        for (String word : words) {
            for (String state : states) {
                if (disjoint(word.toCharArray(), state.toCharArray())) {
                    stateMacks.put(state, stateMacks.get(state) + 1);
                }
            }
        }
        System.out.println();
        int maxVal = Collections.max(stateMacks.values());
        for (String state : stateMacks.keySet()) {
            if (stateMacks.get(state) == maxVal) {
                System.out.println(state);
            }
        }
    }

    /**
     * Returns whether or not two char arrays contain any of the same characters.
     */
    private static boolean disjoint(char[] prim, char[] sec) {
        for (Character a : prim) {
            for (Character b : sec) {
                if (("" + a).equalsIgnoreCase("" + b)) {
                    return false;
                }
            }
        }
        return true;
    }
}
