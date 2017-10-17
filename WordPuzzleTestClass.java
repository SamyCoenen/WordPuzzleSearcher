import org.junit.*;

import static org.junit.Assert.*;

/**
 * Test class for the implementation of the {@link WordPuzzle}
 * @author Jeroen Van Aken
 *
 */
public class WordPuzzleTestClass {

    WordPuzzle myPuzzle = null;

    /**
     * This function will initialize the myPuzzle variable before you start a new test method
     * @throws Exception
     */
    @Before
    public void setUp() {
        try {
            this.myPuzzle = new WordPuzzle("VNYBKGSRORANGEETRNXWPLAEALKAPMHNWMRPOCAXBGATNOMEL", 7);
        } catch (IllegalArgumentException ex) {
            System.out.println("An exception has occured");
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Test the constructor of the {@link WordPuzzle} class
     */
    @Test
    public void testWordPuzzle() {
        assertNotNull("The object failed to initialize", this.myPuzzle);
        char[][] expectedArray = {{'V','N','Y','B','K','G','S'},
                {'R','O','R','A','N','G','E'},
                {'E','T','R','N','X','W','P'},
                {'L','A','E','A','L','K','A'},
                {'P','M','H','N','W','M','R'},
                {'P','O','C','A','X','B','G'},
                {'A','T','N','O','M','E','L'}};
        assertArrayEquals(expectedArray, this.myPuzzle.getLetterArray());
    }

    /**
     * Test to search for some words...
     */
    @Test
    public void testSearchWord() {

        assertFalse("The word SOFTWARE is found, and may not be found", this.myPuzzle.searchWord("SOFTWARE"));
        assertTrue("The word BANANA is not found", this.myPuzzle.searchWord("BANANA"));
    }



}