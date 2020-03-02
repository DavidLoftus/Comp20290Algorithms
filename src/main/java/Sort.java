import sorting.*;

import java.util.*;
import java.util.stream.Collectors;

public class Sort {
    static class ArrayContainer {
        List<Integer> list = null;

        private void generateList(int n) {
            if (list == null || list.size() != n) {
                list = new ArrayList<>(n);
                for (int i = 0; i < n; ++i) {
                    list.add(i);
                }
            }
            Collections.shuffle(list);
        }
    }

    public static void main(String[] args) {

        ArrayContainer arr = new ArrayContainer();

        List<Sorter<Integer>> sorters = Arrays.asList(
                new SelectionSort<>(),
                new InsertionSort<>(),
                new StalinSort<>(),
                new MergeSort<>(),
                new QuickSort<>()
        );

        // Run a few times to get JIT compiler warmed up
        for (int i = 0; i < 100; ++i) {
            for (Sorter<Integer> sorter : sorters) {
                arr.generateList(100);
                sorter.sort(arr.list);
            }
        }

        System.out.print("    n ");
        for (Sorter<Integer> sorter : sorters) {
            System.out.printf("%20s ", sorter.getClass().getSimpleName());
        }
        System.out.println();

        for (int i = 100; i <= 2000; i += 100) {
            final int n = i;
            List<TimeIt> timers = sorters.stream().map(
                    sorter -> new TimeIt(
                            () -> arr.generateList(n),
                            () -> sorter.sort(arr.list)
                    )
            ).collect(Collectors.toList());

            List<Long> times = timers.stream().map(timer -> timer.run(100)).collect(Collectors.toList());

            System.out.printf("%5d ", i);
            for (long t : times) {
                System.out.printf("%20f ", t / 1000000.0);
            }
            System.out.println();
        }
    }

}
