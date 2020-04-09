import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrieTest {

    private Trie trie;

    @BeforeEach
    void init() {
        this.trie = new Trie();
    }

    @Test
    void add() {
        trie.add("HELLOWORLD");
        trie.add("HELLOBIGWORLD");

        assertThrows(IllegalArgumentException.class, () -> trie.add("HELLO WORLD"));
        assertThrows(IllegalArgumentException.class, () -> trie.add("HELLOWORLD!"));
    }

    @Test
    void get() {
        trie.add("HELLOWORLD");
        trie.add("HELLOBIGWORLD");

        Trie subTrie = trie.get("HELLO");
        assertThrows(NoSuchElementException.class, () -> trie.get("BABY"));
        assertThrows(IllegalArgumentException.class, () -> trie.get("HELLO "));

        assertNotNull(subTrie.get("WORL"));
        assertNotNull(subTrie.get("BIGWORL"));

        assertThrows(NoSuchElementException.class, () -> subTrie.get("BABY"));
    }

    @Test
    void contains() {
        trie.add("HELLOWORLD");
        trie.add("HELLOBIGWORLD");

        Trie subTrie = trie.get("HELLO");

        assertTrue(subTrie.contains("WORLD"));
        assertTrue(subTrie.contains("BIGWORLD"));

        assertFalse(subTrie.contains("WORL"));
        assertFalse(subTrie.contains("BIGWORL"));

        assertFalse(trie.contains("GOODBYEWORLD"));
    }

    @Test
    void iterator() {
        trie.add("HELLOWORLD");
        trie.add("HELLOBIGWORLD");

        {
            Iterator<String> iter = trie.iterator();

            assertTrue(iter.hasNext());
            assertEquals("HELLOWORLD", iter.next());

            assertTrue(iter.hasNext());
            assertEquals("HELLOBIGWORLD", iter.next());

            assertFalse(iter.hasNext());
        }

        {
            Trie subTrie = trie.get("HELLO");
            Iterator<String> iter = subTrie.iterator();

            assertTrue(iter.hasNext());
            assertEquals("WORLD", iter.next());

            assertTrue(iter.hasNext());
            assertEquals("BIGWORLD", iter.next());

            assertFalse(iter.hasNext());
        }

        {
            Trie subTrie = trie.get("HELLOBIG");
            Iterator<String> iter = subTrie.iterator();

            assertTrue(iter.hasNext());
            assertEquals("WORLD", iter.next());

            assertFalse(iter.hasNext());
        }
    }
}
