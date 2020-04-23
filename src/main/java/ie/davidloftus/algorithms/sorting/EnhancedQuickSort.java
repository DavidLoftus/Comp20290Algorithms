package ie.davidloftus.algorithms.sorting;

import java.util.Collections;
import java.util.List;

public class EnhancedQuickSort<T extends Comparable<T>> extends QuickSort<T> {

    private List<T> recursiveSort(List<T> arr) {
        if (arr.size() <= 5) {
            Sorter<T> sorter = new InsertionSort<>();
            return sorter.sort(arr);
        }

        int pivotIdx = partition(arr);
        recursiveSort(arr.subList(0, pivotIdx));
        recursiveSort(arr.subList(pivotIdx+1, arr.size()));

        return arr;
    }

    @Override
    int partition(List<T> arr) {
        int i = -1;

        T pivot = arr.get(arr.size()-1);

        for (int j = 0; j < arr.size(); ++j) {
            if (arr.get(j).compareTo(pivot) < 0) {
                ++i;
                // Swap arr[i] and arr[j]
                Sorter.swap(arr, i, j);
            }
        }

        // Swap arr[i+1] and arr[head]
        Sorter.swap(arr, i+1, arr.size()-1);

        return i+1;
    }

    @Override
    public List<T> sort(List<T> arr) {
        Collections.shuffle(arr);
        return recursiveSort(arr);
    }


}
