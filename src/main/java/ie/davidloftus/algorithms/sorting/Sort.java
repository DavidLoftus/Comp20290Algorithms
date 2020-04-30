package ie.davidloftus.algorithms.sorting;

import ie.davidloftus.algorithms.bench.TimeIt;
import ie.davidloftus.algorithms.sorting.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Sort {
    static class ArrayContainer {
        List<Integer> list = null;

        private List<Integer> generateList(int n) {
            if (list == null || list.size() != n) {
                list = new ArrayList<>(n);
                for (int i = 0; i < n; ++i) {
                    list.add(i);
                }
            }
            Collections.shuffle(list);
            return list;
        }
    }

    public static void main(String[] args) {

        ArrayContainer arr = new ArrayContainer();

        List<Sorter<Integer>> sorters = Arrays.asList(
                new SelectionSort<>(),
                new InsertionSort<>(),
                new StalinSort<>(),
                new MergeSort<>(),
                new MergeSortEnhanced<>(),
                new QuickSort<>(),
                new EnhancedQuickSort<>()
        );

        // Run a few times to get JIT compiler warmed up
        for (int i = 0; i < 100; ++i) {
            for (Sorter<Integer> sorter : sorters) {
                arr.generateList(100);
                sorter.sort(arr.list);
            }
        }

        System.out.printf("|%5s |", "n");
        for (Sorter<Integer> sorter : sorters) {
            System.out.printf("%20s |", sorter.getClass().getSimpleName());
        }
        System.out.println();
        System.out.println("|------|" + ("-".repeat(21)+"|").repeat(sorters.size()));

        for (int i = 100; i <= 2000; i += 100) {
            final int n = i;
            List<Long> times = sorters.stream()
                    .map(sorter -> TimeIt.run(
                            TimeUnit.MILLISECONDS.toNanos(250),
                            () -> arr.generateList(n),
                            sorter::sort
                            ))
                    .collect(Collectors.toList());

            System.out.printf("|%5d |", i);
            for (long t : times) {
                System.out.printf("%20f |", t / 1000000.0);
            }
            System.out.println();
        }
    }

}
