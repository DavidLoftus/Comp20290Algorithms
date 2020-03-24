package substrings;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSearch implements Searcher {
    private boolean substringMatches(String text, String pattern, int offset) {
        for (int i = 0; i < pattern.length(); ++i) {
            if (text.charAt(offset+i) != pattern.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int findFirst(String text, String pattern) {
        for (int i = 0; i + pattern.length() <= text.length(); ++i) {
            if (substringMatches(text, pattern, i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Integer> findAll(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        for (int i = 0; i + pattern.length() <= text.length(); ++i) {
            if (substringMatches(text, pattern, i)) {
                matches.add(i);
            }
        }
        return matches;
    }
}
