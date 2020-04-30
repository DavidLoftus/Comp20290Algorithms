package ie.davidloftus.algorithms.substrings;

import ie.davidloftus.algorithms.bench.TimeIt;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class Bench {

    private static class Testcase {
        String needle;
        String haystack;

        String description;

        public Testcase(String needle, String haystack, String description) {
            this.needle = needle;
            this.haystack = haystack;
            this.description = description;
        }

        public Testcase(String needle, String haystack) {
            this(needle, haystack, needle + ":" + haystack);
        }
    }

    private static final List<Searcher> searchers = List.of(new BruteForceSearch(), new KMPSearch());

    public static void runTestCase(Testcase tc) {
        System.out.printf("|%30s |", tc.description);
        for (Searcher searcher : searchers) {
            long oneSecondNanos = TimeUnit.MILLISECONDS.toNanos(100);
            long time = TimeIt.run(oneSecondNanos, () -> {}, () -> searcher.findFirst(tc.haystack, tc.needle));
            System.out.printf("%20.4f |", time / 1000000.0);
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        List<Testcase> testcases = List.of(
                new Testcase("needle", "haystack", "tiny string not present"),
                new Testcase("character", "kinda big string with lots of characters", "small string, present, end"),
                new Testcase("character", "kinda big string with lots of characters".repeat(10), "repeated string, short circuit"),
                new Testcase("racecar", "racec".repeat(1000) + "racecar", "100 false positives"),
                new Testcase("e".repeat(256) + "yyy!", "e".repeat(1024*1024) + "yyy!", "super long needle and haystack")
                );

        System.out.printf("|%30s |", "Description");
        for (Searcher searcher : searchers) {
            System.out.printf("%20s |", searcher.getClass().getSimpleName());
        }
        System.out.println();
        System.out.println("|" + "-".repeat(31) + "|" + "---------------------|".repeat(searchers.size()));
        testcases.forEach(Bench::runTestCase);
    }

}
