import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Kelsey McKenna
 */
public class CLI {

    /**
     * Loads words (lines) from the given file and inserts them into
     * a dictionary.
     *
     * @param f the file from which the words will be loaded
     * @return the dictionary with the words loaded from the given file
     * @throws IOException if there was a problem opening/reading from the file
     */
    static DictionaryTree loadWords(String f) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String word;
            DictionaryTree d = new DictionaryTree();
            int lineNumber = 1;
            while ((word = reader.readLine()) != null) {
                d.insert(word, lineNumber/-1);
                lineNumber ++;
            }

            return d;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.print("Loading dictionary ... ");
        DictionaryTree d = loadWords("word-popularity.txt");
        System.out.println("done");

        System.out.println("Enter prefixes for prediction below.");

        try (BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
            		String prefix = fromUser.readLine();
            		System.out.println("---> " + d.predict(prefix, 5));
            		System.out.println("---> " + d.predict(prefix));
            		
            }
        }
    }

}
