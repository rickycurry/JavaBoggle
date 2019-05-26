package tests;

import model.DictionaryTree;
import model.exceptions.LetterNotInChildrenException;
import parsers.DictionaryTreeParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Arrays;

import static model.DictionaryTree.addWordToTree;
import static model.DictionaryTree.containsWord;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTreeTest {

    private HashSet<DictionaryTree> emptyList = new HashSet<>();
    private DictionaryTree d4 = new DictionaryTree('d', true, emptyList);
    private DictionaryTree r3 = new DictionaryTree('r', false, new HashSet<>(Arrays.asList(d4)));
    private DictionaryTree i2 = new DictionaryTree('i', false, new HashSet<>(Arrays.asList(r3)));
    private DictionaryTree g3 = new DictionaryTree('g', true, emptyList);
    private DictionaryTree t3 = new DictionaryTree('t', true, emptyList);
    private DictionaryTree a2 = new DictionaryTree('a', false, new HashSet<>(Arrays.asList(t3, g3)));
    private DictionaryTree b1 = new DictionaryTree('b', false, new HashSet<>(Arrays.asList(a2, i2)));
    private DictionaryTree y3 = new DictionaryTree('y', true, emptyList);
    private DictionaryTree t3Left = new DictionaryTree('t', true, emptyList);
    private DictionaryTree n2 = new DictionaryTree('n', true, new HashSet<>(Arrays.asList(t3Left, y3)));
    private DictionaryTree t2 = new DictionaryTree('t', true, emptyList);
    private DictionaryTree a1 = new DictionaryTree('a', true, new HashSet<>(Arrays.asList(n2, t2)));
    private DictionaryTree topTree = new DictionaryTree(' ', false, new HashSet<>(Arrays.asList(a1, b1)));


    @Test
    public void testHasLetterInChildren() {
        assertTrue(topTree.containsLetter('a'));
        assertTrue(topTree.containsLetter('b'));
        assertFalse(topTree.containsLetter('c'));
        assertFalse(d4.containsLetter('a'));
    }

    @Test
    public void testGetTreeByLetter() {
        try {
            DictionaryTree child = i2.getTreeByLetter('r');
            assertTrue(child.containsLetter('d'));
            assertFalse(topTree.containsLetter('e'));
        } catch (LetterNotInChildrenException e) {
            fail();
        }
        try {
            DictionaryTree child = topTree.getTreeByLetter('c');
            fail();
        } catch (LetterNotInChildrenException e) {
            //
        }
    }

    @Test
    public void testContainsWords() {
        assertTrue(containsWord("bird", topTree));
        assertFalse(containsWord("bir", topTree));
        assertTrue(containsWord("bag", topTree));
        assertTrue(containsWord("d", r3));
        assertFalse(containsWord("abc", topTree));
    }

    @Test
    public void testHasChildrenForSubstring() {
        assertTrue(topTree.hasChildrenForSubstring("bir", topTree));
        assertTrue(topTree.hasChildrenForSubstring("b", topTree));
        assertFalse(topTree.hasChildrenForSubstring("bird", topTree));
        assertFalse(topTree.hasChildrenForSubstring("bil", topTree));
        assertFalse(topTree.hasChildrenForSubstring("birdo", topTree));
        assertFalse(topTree.hasChildrenForSubstring("atn", topTree));
        assertFalse(topTree.hasChildrenForSubstring("x", topTree));
        assertFalse(topTree.hasChildrenForSubstring("at", topTree));
    }

    @Test
    public void testAddChild() {
        assertFalse(topTree.containsLetter('c'));
        topTree.addChild('c', false, new HashSet<>());
        assertTrue(topTree.containsLetter('c'));
    }

    @Test
    public void testAddWordToTree() {
        assertFalse(containsWord("taco", topTree));
        addWordToTree("taco", topTree);
        assertTrue(containsWord("taco", topTree));
    }

    @Test
    public void testFullSerializedDictionary() {
        DictionaryTree fullDictionary = new DictionaryTree();
        assertTrue(containsWord("battleaxe", fullDictionary));
        assertFalse(containsWord("abcde", fullDictionary));
        assertTrue(containsWord("mink", fullDictionary));
    }

    @Test
    public void testDictionaryTreeParserException() {
        String oldPath = "./src/main/dictionary/dictionary.txt";
        String newPath = "./src/main/dictionary/hijinx.txt";
        File file1 = new File(oldPath);
        File file2 = new File(newPath);
        assertTrue(file1.renameTo(file2));
        DictionaryTree dt = DictionaryTreeParser.parse();
        assertFalse(containsWord("blue", dt));
        assertTrue(file2.renameTo(file1));
    }
}
