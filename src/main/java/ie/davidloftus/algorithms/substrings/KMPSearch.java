package ie.davidloftus.algorithms.substrings;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch implements Searcher {

    private int[] createPatternTable(String pattern) {
        int[] table = new int[pattern.length()+1];
        table[0] = -1;

        int candidatePos = 0;

        for (int i = 1; i < pattern.length(); i++, candidatePos++) {
            if (pattern.charAt(i) == pattern.charAt(candidatePos)) {
                table[i] = table[candidatePos];
            } else {
                table[i] = candidatePos;
                candidatePos = table[candidatePos];
                while (candidatePos >= 0 && pattern.charAt(i) != pattern.charAt(candidatePos)) {
                    candidatePos = table[candidatePos];
                }
            }
        }

        table[pattern.length()] = candidatePos;

        return table;
    }

    @Override
    public int findFirst(String text, String pattern) {
        int[] table = createPatternTable(pattern);

        int k = 0;

        for (int j = 0; j < text.length();) {
            if (pattern.charAt(k) == text.charAt(j)) {
                ++j;
                ++k;
                if (k == pattern.length()) {
                    return j - k;
                }
            } else {
                k = table[k];
                if (k < 0) {
                    ++j;
                    ++k;
                }
            }
        }

        return -1;
    }

    @Override
    public List<Integer> findAll(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int[] table = createPatternTable(pattern);

        int k = 0;

        for (int j = 0; j < text.length();) {
            if (pattern.charAt(k) == text.charAt(j)) {
                ++j;
                ++k;
                if (k == pattern.length()) {
                    matches.add(j - k);
                    k = table[k];
                }
            } else {
                k = table[k];
                if (k < 0) {
                    ++j;
                    ++k;
                }
            }
        }

        return matches;
    }
}
