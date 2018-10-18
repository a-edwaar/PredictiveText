import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class DictionaryTree {

	private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
	private Optional<Integer> pop = Optional.empty();

	/**
	 * Inserts the given word into this dictionary. If the word already exists,
	 * nothing will change.
	 *
	 * @param word
	 *            the word to insert
	 */
	void insert(String word) {
		insert(word, 0);
	}

	/**
	 * Inserts the given word into this dictionary with the given popularity. If the
	 * word already exists, the popularity will be overriden by the given value.
	 *
	 * @param word
	 *            the word to insert
	 * @param popularity
	 *            the popularity of the inserted word
	 */
	void insert(String word, int popularity) {
		
		assert(word!=null && !word.isEmpty());

		insertIntoTree(word);
		dictOfLastLetter(word).get().pop = Optional.of(popularity);
	}

	/**
	 * Removes the specified word from this dictionary. Returns true if the caller
	 * can delete this node without losing part of the dictionary, i.e. if this node
	 * has no children after deleting the specified word.
	 *
	 * @param word
	 *            the word to delete from this dictionary
	 * @return whether or not the parent can delete this node from its children
	 */
	boolean remove(String word) {

		boolean canBeRemoved = false;
		
		assert(word!=null && !word.isEmpty());

		if (dictOfLastLetter(word).isPresent()) {

			// delete the pop from the word we want to delete
			DictionaryTree dict = dictOfLastLetter(word).get();
			dict.pop = Optional.empty();

			String deleteWord = word;
			while (deleteWord.length() > 0) {

				dict = dictOfLastLetter(deleteWord).get();
				Map<Character, DictionaryTree> m = mapOfLastLetter(deleteWord);

				if (dict.size() > 1) {
					// if last letter of the word in map has children, can't delete it
					break;
				} else if (dict.size() == 1 && !(dict.pop.isPresent())) {
					char letter = deleteWord.charAt(deleteWord.length() - 1);
					m.remove(letter);
					deleteWord = deleteWord.substring(0, deleteWord.length() - 1);
					canBeRemoved = true;
				} else {
					break;
				}
			}
		}

		return canBeRemoved;

	}

	/**
	 * Determines whether or not the specified word is in this dictionary.
	 *
	 * @param word
	 *            the word whose presence will be checked
	 * @return true if the specified word is stored in this tree; false otherwise
	 */
	boolean contains(String word) {
		
		assert(word!=null && !word.isEmpty());

		boolean isValidWord = false;

		if (dictOfLastLetter(word).isPresent()) {
			DictionaryTree dict = dictOfLastLetter(word).get();
			if (dict.pop.isPresent()) {
				isValidWord = true;
			}
		}

		return isValidWord;

	}

	/**
	 * @param prefix
	 *            the prefix of the word returned
	 * @return a word that starts with the given prefix, or an empty optional if no
	 *         such word is found.
	 */
	Optional<String> predict(String prefix) {
		
		assert(prefix!=null && !prefix.isEmpty());

		Optional<String> returnedWord = Optional.empty();

		ArrayList<String> sortedWords = new ArrayList<>();

		if (dictOfLastLetter(prefix).isPresent()) {

			DictionaryTree prefixTree = dictOfLastLetter(prefix).get();

			sortedWords = (ArrayList<String>) commonPredictCode(prefixTree, prefix);

			if (sortedWords.size() > 0) {
				String restOfWord = sortedWords.get(0);
				if (restOfWord.equals(prefix)) {
					returnedWord = Optional.of(prefix);
				} else {
					returnedWord = Optional.of(prefix + restOfWord);
				}
			}

		}

		return returnedWord;
	}

	/**
	 * Predicts the (at most) n most popular full English words based on the
	 * specified prefix. If no word with the specified prefix is found, an empty
	 * list is returned.
	 *
	 * @param prefix
	 *            the prefix of the words found
	 * @return the (at most) n most popular words with the specified prefix
	 */
	List<String> predict(String prefix, int n) {
		
		assert(prefix!=null && !prefix.isEmpty());

		List<String> words = new ArrayList<>();

		ArrayList<String> sortedWords = new ArrayList<>();

		if (dictOfLastLetter(prefix).isPresent()) {

			// get all words from the prefix.
			DictionaryTree prefixTree = dictOfLastLetter(prefix).get();

			sortedWords = (ArrayList<String>) commonPredictCode(prefixTree, prefix);

			int sizeOfList;
			if (sortedWords.size() >= n) {
				sizeOfList = n;
			} else {
				sizeOfList = sortedWords.size();
			}

			// return the first n numbers of the list
			for (int i = 0; i < sizeOfList; i++) {
				String word = sortedWords.get(i);
				String wholeWord;
				if (word.equals(prefix)) {
					wholeWord = prefix;
				} else {
					wholeWord = prefix + word;
				}
				words.add(wholeWord);
			}

		}

		return words;
	}

	/**
	 * @return the number of leaves in this tree, i.e. the number of words which are
	 *         not prefixes of any other word.
	 */
	int numLeaves() {
		
		return (int)fold((A,B) -> {
			
			if(A.children.isEmpty()) {
				B.add(1);
			}
			
			int numOfLeaves = 0;
			for(Object o:B) {
				numOfLeaves += (int)o;
			}
			
			
			return numOfLeaves;
		});
	}

	/**
	 * @return the maximum number of children held by any node in this tree
	 */
	int maximumBranching() {
		
		return (int)fold((A,B) -> {
			
			int n = A.children.size();
			int max = 0;
			
			
			for(Object o:B) {
				max = Math.max((int)o, max);
			}
			
			max = Math.max(max,n);
			
			return max;
		});
		
		
	}

	/**
	 * @return the height of this tree, i.e. the length of the longest branch
	 */
	int height() {
		
		return (int)fold((A,B) -> {
			
			int maxHeight = 0;
			
			for (Object o:B) {
				maxHeight = Math.max((int)o, maxHeight);
			}

			return maxHeight + 1;
			
		}) -1;

	}

	/**
	 * @return the number of nodes in this tree
	 */
	int size() {
		
		return (int) fold((A, B) -> {
			
			int total = 0;
			for(Object o:B) {
				total += (int)o;
			}
			
			return total + 1;
			
		});

	}

	/**
	 * @return the longest word in this tree
	 */
	String longestWord() {

		String word = "";
	
		Optional<Character> nodeWithMaxHeight = Optional.empty();
		int maxHeight = 0;

		for (char key : children.keySet()) {
			if (children.get(key).height() >= maxHeight) {
				nodeWithMaxHeight = Optional.of(key);
				maxHeight = children.get(key).height();
			}
		}
		if (nodeWithMaxHeight.isPresent()) {
			word += nodeWithMaxHeight.get();
			word += children.get(nodeWithMaxHeight.get()).longestWord();
		}

		return word;

	}

	/**
	 * @return all words stored in this tree as a list
	 */
	List<String> allWords() {
		return allWords("");
	}

	/**
	 * Folds the tree using the given function. Each of this node's children is
	 * folded with the same function, and these results are stored in a collection,
	 * cResults, say, then the final result is calculated using f.apply(this,
	 * cResults).
	 *
	 * @param f
	 *            the summarising function, which is passed the result of invoking
	 *            the given function
	 * @param <A>
	 *            the type of the folded value
	 * @return the result of folding the tree using f
	 */
	<A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {

		ArrayList<A> cResults = new ArrayList<>();
		
		for(char child: children.keySet()) {
			cResults.add(children.get(child).fold(f));
		}
		
		return f.apply(this, cResults);
		
	}

	// helper functions

	// insertion into tree
	private void insertIntoTree(String word) {
		char letter = word.charAt(0);

		// if the letter is not present in the tree add it
		if (!children.containsKey(letter)) {
			children.put(letter, new DictionaryTree());
		}

		// if the rest of the word is not null, recursively go through the remaining
		// letters
		if (!word.substring(1).equals("")) {
			children.get(letter).insert(word.substring(1));
		}
	}

	// get dictionary tree of last letter in a word
	private Optional<DictionaryTree> dictOfLastLetter(String word) {

		char letter = word.charAt(0);

		if (children.keySet().contains(letter)) {
			if (word.length() == 1) {
				return Optional.of(children.get(letter));
			} else {
				return children.get(letter).dictOfLastLetter(word.substring(1));
			}
		} else {
			return Optional.empty();
		}

	}

	// get the map that the last letter of the word is in
	private Map<Character, DictionaryTree> mapOfLastLetter(String word) {

		char letter = word.charAt(0);

		if (word.length() == 1) {
			return children;
		} else {
			return children.get(letter).mapOfLastLetter(word.substring(1));
		}

	}

	private List<String> commonPredictCode(DictionaryTree prefixTree, String prefix) {

		List<String> sortedWords = new ArrayList<>();

		if (prefixTree.size() > 1) {

			ArrayList<String> allPossibleWords = (ArrayList<String>) prefixTree.allWords();

			// add to the possible words the original prefix if it is a word.
			if (contains(prefix)) {
				allPossibleWords.add(prefix);
			}

			sortedWords = (ArrayList<String>) sortWordsByPop(prefix, prefixTree, allPossibleWords);

		}

		return sortedWords;

	}

	// return sorted list of words by popularity from prefix
	private List<String> sortWordsByPop(String prefix, DictionaryTree prefixTree, ArrayList<String> allPossibleWords) {

		DictionaryTree dict;
		List<String> sortedWords = new ArrayList<>();
		List<Integer> sortedPops = new ArrayList<>();

		for (String word : allPossibleWords) {
			if (word.equals(prefix)) {
				dict = prefixTree;
			} else {
				// get the dictionary of the last in that word
				dict = prefixTree.dictOfLastLetter(word).get();
			}

			sortedWords.add(word);
			sortedPops.add(dict.pop.get());
		}

		// sort the two lists together so that the word with the biggest pop is at the
		// start of the list
		int tempForPop;
		String tempForWord;
		for (int i = 0; i < sortedPops.size(); i++) {
			for (int j = 1; j < (sortedPops.size() - i); j++) {
				if (sortedPops.get(j - 1) < sortedPops.get(j)) {
					// swap elements
					tempForPop = sortedPops.get(j - 1);
					tempForWord = sortedWords.get(j - 1);
					sortedPops.set(j - 1, sortedPops.get(j));
					sortedWords.set(j - 1, sortedWords.get(j));
					sortedPops.set(j, tempForPop);
					sortedWords.set(j, tempForWord);
				}
			}
		}

		return sortedWords;
	}

	// return all words in the tree.
	private List<String> allWords(String currentWord) {

		List<String> words = new ArrayList<>();

		for (char key : children.keySet()) {

			if (children.get(key).pop.isPresent()) {
				words.add(currentWord + key);
			}

			words.addAll(children.get(key).allWords(currentWord + key));
		}

		return words;

	}

}
