import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;

/**
 * @author Archie Edwards
 */
public class DictionaryTreeTests {

	//height tests
    @Test
    public void heightOfRootShouldBeZero() {
        DictionaryTree unit = new DictionaryTree();
        Assertions.assertEquals(0, unit.height());
    }

    @Test
    public void heightOfWordShouldBeWordLength() {
        DictionaryTree unit = new DictionaryTree();
        unit.insert("word", 0);
        Assertions.assertEquals("word".length(), unit.height());
    }
    
    @Test
    public void heightShouldBeLongestWordLength() {
    		DictionaryTree unit = new DictionaryTree();
        unit.insert("word", 0);
        unit.insert("apple",0);
        Assertions.assertEquals("apple".length(), unit.height());
    }
    
    //size tests
    @Test
    public void sizeOfTreeShouldBeOne() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertEquals(1, unit.size());
    }
    
    @Test
    public void sizeOfTreeShouldBeWordLengthPlusOne() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello");
		Assertions.assertEquals(6, unit.size());
    }
    
    @Test
    public void sizeShouldBeAllLettersPlusOne() {
    		DictionaryTree unit = new DictionaryTree();
		unit.insert("hello");
		unit.insert("morning");
		Assertions.assertEquals(13, unit.size());
    }
    
    //contains test
    @Test
    public void treeShouldContainWord() {
    		DictionaryTree unit = new DictionaryTree();
        unit.insert("word");
        Assertions.assertTrue(unit.contains("word"));
    }
    
    @Test
    public void treeShouldNotContainWord() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertFalse(unit.contains("word"));
    }
    
    //remove tests
  
    @Test
    public void treeShouldNotContainWordAfterRemove() {
    		DictionaryTree unit = new DictionaryTree();
        unit.insert("word");
        unit.remove("word");
        Assertions.assertFalse(unit.contains("word"));
    }
    
    @Test
    public void treeShouldRemovePopButNotLetters() {
    		DictionaryTree unit = new DictionaryTree();
        unit.insert("word", 0);
        unit.insert("words", 1);
        unit.remove("word");
        Assertions.assertFalse(unit.contains("word"));
    }
    
    @Test
    public void treeShouldStillContainSmallerWord() {
    		DictionaryTree unit = new DictionaryTree();
        unit.insert("words", 0);
        unit.insert("word", 1);
        unit.remove("words");
        Assertions.assertTrue(unit.contains("word"));
    }
    
    //number of leaves tests
    @Test
    public void numberOfLeavesShouldbe1() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertEquals(1, unit.numLeaves());
    }
    
    @Test
    public void numberOfLeavesShouldbe2() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("word");
    		unit.insert("woman");
    		Assertions.assertEquals(2, unit.numLeaves());
    }
    
    @Test
    public void numberOfLeavesShouldbe3() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("word");
    		unit.insert("woman");
    		unit.insert("robber");
    		Assertions.assertEquals(3, unit.numLeaves());
    }
    
    //longest word test
    
    @Test
    public void longestWordShouldReturnEmptyString() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertEquals("", unit.longestWord());
    }
    
    @Test
    public void longestWordShouldBeRobber() {
    		DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		unit.insert("woman");
		unit.insert("robber");
		Assertions.assertEquals("robber", unit.longestWord());
    }
    
    @Test
    public void longestWordShouldBeOneOfTheLongestWords() {
    	DictionaryTree unit = new DictionaryTree();
		unit.insert("word");
		unit.insert("woman");
		unit.insert("robber");
		unit.insert("tomato");
		String word = unit.longestWord();
		Assertions.assertTrue(word.equals("robber") || word.equals("tomato"));
    }
    
    
    //maximum branching test
    @Test
    public void maxBranchesShouldBe0() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertEquals(0, unit.maximumBranching());
    }
    
    @Test
    public void maxBranchesShouldBe3() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("anger");
    		unit.insert("apple");
    		unit.insert("artifact");
    		unit.insert("build");
    		Assertions.assertEquals(3, unit.maximumBranching());
    }
    
    //all words tests
    @Test
    public void allWordsShouldReturnEmptyList() {
    		DictionaryTree unit = new DictionaryTree();
    		List<String> words = unit.allWords();
    		Assertions.assertTrue(words.isEmpty());
    }
    
    @Test
    public void allWordsTestWithMoreThanOneChild() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("word", 3);
    		unit.insert("world", 10);
    		unit.insert("build", 50);
    		List<String> expectedWords = Arrays.asList("word", "world", "build");
    		Assertions.assertEquals(expectedWords, unit.allWords());
    }
    
    @Test
    public void allWordsTestWithOneChild() {
		DictionaryTree unit = new DictionaryTree();
		unit.insert("word", 3);
		unit.insert("words", 10);
		unit.insert("build", 50);
		List<String> expectedWords = Arrays.asList("word", "words", "build");
		Assertions.assertEquals(expectedWords, unit.allWords());
    }
    
    
    //prefix test
    @Test
    public void prefixShouldReturnEmptyOptional() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertFalse(unit.predict("wi").isPresent());
    }
    
    @Test
    public void prefixShouldReturnWords() {
    		DictionaryTree unit = new DictionaryTree();
		unit.insert("word", 3);
		unit.insert("words", 10);
		unit.insert("build", 5);
		Assertions.assertEquals("words", unit.predict("wor").get());
    }
    
    @Test
    public void prefixShouldReturnPrefix() {
    		DictionaryTree unit = new DictionaryTree();
		unit.insert("witch", 50);
		unit.insert("witches", 0);
		unit.insert("witched", 4);
		Assertions.assertEquals("witch", unit.predict("witch").get());
    }
    
    
    //prefix tests with additional parameter
    @Test 
    public void prefixShouldReturnEmptyList() {
    		DictionaryTree unit = new DictionaryTree();
    		Assertions.assertTrue(unit.predict("wo", 5).size() == 0);
    }
    
    @Test
    public void prefixShouldReturnNMostPop() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("wonderful", 4);
    		unit.insert("woman");
    		unit.insert("wonder", 100);
    		unit.insert("wobble", 52);
    		List<String> expectedWords = Arrays.asList("wonder", "wobble", "wonderful");
    		Assertions.assertEquals(expectedWords, unit.predict("wo", 3));
    }
    
    @Test
    public void prefixShouldReturnAllWords() {
    		DictionaryTree unit = new DictionaryTree();
    		unit.insert("wonderful", 4);
    		unit.insert("woman");
    		unit.insert("wonder", 100);
    		unit.insert("wobble", 52);
    		List<String> expectedWords = Arrays.asList("wonder", "wobble", "wonderful", "woman");
    		Assertions.assertEquals(expectedWords, unit.predict("wo", 10));
    }
    
}
