package ie.davidloftus.algorithms.compression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiRunLengthTest {

    @Test
    void compress() {

        assertEquals("h1e1l2o1w1o1r1l1d1", AsciiRunLength.compress("helloworld"));
        assertEquals("a16b5c1", AsciiRunLength.compress("aaaaaaaaaaaaaaaabbbbbc"));

    }
}