package ie.davidloftus.algorithms.sorting;

import java.util.List;

public class QuickSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public boolean isInPlace() {
        return true;
    }

    @Override
    public List<T> sort(List<T> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int pivotIdx = partition(arr);
        sort(arr.subList(0, pivotIdx));
        sort(arr.subList(pivotIdx+1, arr.size()));

        return arr;
    }

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
}
