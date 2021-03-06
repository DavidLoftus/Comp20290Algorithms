package ie.davidloftus.algorithms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Trie implements Iterable<String> {

    // ie.davidloftus.algorithms.sorting.Sort alphabet based on letter frequency in English language.
    // Means we can minimize average size of children array.
    private static final String ALPHABET = "ETAOINSRHDLUCMFYWGPBVKXQJZ";

    // Keep size of children minimal to fit only non-null child nodes.
    private Trie[] children = null;

    // Store isEnd values as bitset in parent node to avoid redundant leaf nodes.
    private int endChars = 0;

    private static int charToIndex(char c) {
        int i = ALPHABET.indexOf(c);
        if (i == -1) {
            throw new IllegalArgumentException("invalid char: " + c);
        }
        return i;
    }

    private static char indexToChar(int i) {
        return ALPHABET.charAt(i);
    }

    private int childrenSize() {
        return children == null ? 0 : children.length;
    }

    private void growToFit(int n) {
        int powerOfTwo = 1;
        while (powerOfTwo < n) {
            powerOfTwo <<= 1;
        }
        powerOfTwo = Math.min(powerOfTwo, ALPHABET.length());
        if (powerOfTwo > childrenSize()) {
            if (children == null) {
                children = new Trie[powerOfTwo];
            } else {
                children = Arrays.copyOf(children, powerOfTwo);
            }
        }
    }

    private void addEnd(char c) {
        int i = charToIndex(c);
        endChars |= (1 << i);
    }

    private boolean isEnd(char c) {
        int i = charToIndex(c);
        return isEnd(i);
    }

    private boolean isEnd(int i) {
        return ((endChars >> i) & 1) != 0;
    }

    public Trie add(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("can't add empty string");
        }

        // Run assertion on validity of chars before mutating trie to ensure operation is
        // transactional.
        s.chars().forEach(c -> charToIndex((char) c));

        Trie subTrie = this;
        for (int i = 0; i < s.length() - 1; ++i) {
            subTrie = subTrie.addArc(s.charAt(i));
        }

        subTrie.addEnd(s.charAt(s.length() - 1));

        return subTrie;
    }

    private Trie addArc(char c) {
        int i = charToIndex(c);
        growToFit(i + 1);
        if (children[i] == null) {
            children[i] = new Trie();
        }
        return children[i];
    }

    public Trie get(char c) {
        int i = charToIndex(c);
        growToFit(i + 1);
        if (children[i] == null) {
            throw new NoSuchElementException("char '" + c + "' not in trie.");
        }
        return children[i];
    }

    public Trie get(String s) {
        Trie subTrie = this;
        for (int i = 0; i < s.length(); ++i) {
            subTrie = subTrie.get(s.charAt(i));
        }
        return subTrie;
    }

    public boolean contains(String s) {
        try {
            Trie subTrie = this;
            for (int i = 0; i < s.length() - 1; ++i) {
                subTrie = subTrie.get(s.charAt(i));
            }
            return subTrie.isEnd(s.charAt(s.length() - 1));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private class StatefulTrieCollector {
        private boolean firstIter = true;
        private int childIdx = 0;
        private StatefulTrieCollector childCollector;

        private char takenLetter;

        public StatefulTrieCollector() {}

        private boolean shouldSkipChild(int i) {
            return (i >= childrenSize() || children[i] == null) && !isEnd(i);
        }

        private void advanceToNextNonNullChild() {
            while (childIdx < ALPHABET.length() && shouldSkipChild(childIdx)) {
                childIdx++;
            }
        }

        boolean tryNext(StringBuffer buffer) {
            if (firstIter) {
                advanceToNextNonNullChild();
            }

            // Since we are using lazy iterators, we need alot of ugly stateful code.
            // This loop proceeds until we found a result to yield
            // If none is found we escape and return false
            while (childIdx < ALPHABET.length()) {
                char c = indexToChar(childIdx);
                if (firstIter) {
                    firstIter = false;

                    // Append character to buffer
                    // All subsequent calls to tryNext will have this character present
                    // At least until we switch to next character
                    buffer.append(c);

                    if (isEnd(c)) {
                        return true;
                    }
                }

                if (childIdx < childrenSize() && children[childIdx] != null) {
                    if (childCollector == null) {
                        childCollector = children[childIdx].getCollector();
                    }
                    if (childCollector.tryNext(buffer)) {
                        return true;
                    }
                }
                // We are finished with this character, clean up collector to proceed to next state
                buffer.deleteCharAt(buffer.length() - 1);
                childCollector = null;
                firstIter = true;
                childIdx++;
                advanceToNextNonNullChild();
            }
            return false;
        }
    }

    private StatefulTrieCollector getCollector() {
        return new StatefulTrieCollector();
    }

    private class TrieIterator implements Iterator<String> {
        StatefulTrieCollector collector = getCollector();
        StringBuffer buffer = new StringBuffer();

        public TrieIterator() {}

        boolean resultPending = false;

        @Override
        public boolean hasNext() {
            if (!resultPending) {
                resultPending = collector.tryNext(buffer);
            }
            return resultPending;
        }

        @Override
        public String next() {
            assert hasNext();
            resultPending = false;
            return buffer.toString();
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

}
