package ie.davidloftus.algorithms.threesum;

import ie.davidloftus.algorithms.bench.TimeIt;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ThreeSumMain {

    static int[] listToArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer x : list) {
            arr[i++] = x;
        }
        return arr;
    }

    static final String[] samples = {
            "8ints.txt",
            "1Kints.txt",
            "2Kints.txt",
            "4Kints.txt",
            "8Kints.txt",
            "16Kints.txt",
            "32Kints.txt"
    };

    public static void forEachTestCase(Consumer<int[]> func) {
        for (String path : samples) {
            System.out.println(path);
            try (Scanner sc = new Scanner(ThreeSumMain.class.getResourceAsStream(path))) {
                List<Integer> list = new ArrayList<>();
                int n = sc.nextInt();
                while (sc.hasNextInt()) {
                    list.add(sc.nextInt());
                }
                func.accept(listToArray(list));
            }
        }
    }

    public static void main(String[] args) {
        forEachTestCase(arr -> {
            long aTime = TimeIt.run(TimeUnit.MILLISECONDS.toNanos(250),
                    () -> arr,
                    ThreeSumA::count);
            System.out.printf("\tie.davidloftus.algorithms.threesum.ThreeSumA: %.3fms\n", aTime / 1000000.0);

            long bTime = TimeIt.run(TimeUnit.MILLISECONDS.toNanos(250),
                    () -> arr,
                    ThreeSumB::count);
            System.out.printf("\tie.davidloftus.algorithms.threesum.ThreeSumB: %.3fms\n", bTime / 1000000.0);
        });
    }
}
