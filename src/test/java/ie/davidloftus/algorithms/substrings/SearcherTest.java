package ie.davidloftus.algorithms.substrings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SearcherTest {
    @ParameterizedTest
    @ValueSource(classes = {BruteForceSearch.class, KMPSearch.class})
    void findFirst(Class<? extends Searcher> searcherClass) throws ReflectiveOperationException {
        Searcher searcher = searcherClass.getConstructor().newInstance();

        assertEquals(0, searcher.findFirst("Hello World", "Hello World"));
        assertEquals(6, searcher.findFirst("Hello World", "Wor"));
        assertEquals(4, searcher.findFirst("Hel World", "Wor"));

        assertEquals(-1, searcher.findFirst("Hello World", "Goodbye World"));
        assertEquals(-1, searcher.findFirst("Hello World", "Hello Planet"));
    }
}