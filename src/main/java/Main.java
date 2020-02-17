import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static int[] listToArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer x : list) {
            arr[i] = x;
        }
        return arr;
    }

    public static void forEachTestCase(Consumer<int[]> func) {
        try (Stream<Path> walk = Files.walk(Paths.get("."))) {

            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            for (String path : result) {
                System.out.println(path);
                try (Scanner sc = new Scanner(new File(path))) {
                    List<Integer> list = new ArrayList<>();
                    while (sc.hasNextInt()) {
                        list.add(sc.nextInt());
                    }
                    func.accept(listToArray(list));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static long timeIt(Runnable func) {
        long timeStart = System.nanoTime();
        func.run();
        long timeEnd = System.nanoTime();

        return timeEnd - timeStart;
    }

    public static void main(String[] args) {
        forEachTestCase(arr -> {
            long aTime = timeIt(() -> ThreeSumA.count(arr));
            System.out.printf("\tThreeSumA: %.3fms\n", aTime / 1000000.0);

            long bTime = timeIt(() -> ThreeSumB.count(arr));
            System.out.printf("\tThreeSumB: %.3fms\n", bTime / 1000000.0);
        });
    }
}
