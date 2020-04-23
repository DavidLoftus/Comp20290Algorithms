package ie.davidloftus.algorithms.substrings;

import java.util.List;

public interface Searcher {

    int findFirst(String text, String pattern);

    List<Integer> findAll(String text, String pattern);

}
