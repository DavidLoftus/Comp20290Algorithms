package sorting;

import java.util.List;

public class InsertionSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public boolean isInPlace() {
        return true;
    }

    @Override
    public List<T> sort(List<T> arr) {
        for (int i = 1; i < arr.size(); ++i) {
            for (int j = i; j > 0 && arr.get(j-1).compareTo(arr.get(j)) > 0; --j) {
                Sorter.swap(arr, j, j-1);
            }
        }
        return arr;
    }
}
