package substrings;

import bench.TimeIt;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class Bench {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new GZIPInputStream(Bench.class.getResourceAsStream("moby10b.txt.gz"));
        String largeString = new String(inputStream.readAllBytes());

        System.out.println("findFirst:");
        for (Searcher searcher : new Searcher[]{new BruteForceSearch(), new KMPSearch()}) {
            long oneSecondNanos = TimeUnit.SECONDS.toNanos(1);
            long time = TimeIt.run(oneSecondNanos, () -> {}, () -> searcher.findAll(largeString, "and then"));
            System.out.printf("\t%s\t%.03fms\n", searcher.getClass().getSimpleName(), time / 1000000.0);
        }

        System.out.println("findFirst:");
        for (Searcher searcher : new Searcher[]{new BruteForceSearch(), new KMPSearch()}) {
            long oneSecondNanos = TimeUnit.SECONDS.toNanos(1);
            long time = TimeIt.run(oneSecondNanos, () -> {}, () -> searcher.findFirst(largeString, "and then"));
            System.out.printf("\t%s\t%.03fms\n", searcher.getClass().getSimpleName(), time / 1000000.0);
        }
    }

}
