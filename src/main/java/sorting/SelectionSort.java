package sorting;

import java.util.List;

public class SelectionSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public boolean isInPlace() {
        return true;
    }

    @Override
    public List<T> sort(List<T> arr) {
        for (int i = 0; i < arr.size()-1; ++i) {
            int minI = i;
            for (int j = i+1; j < arr.size(); ++j) {
                if (arr.get(j).compareTo(arr.get(minI)) < 0) {
                    minI = j;
                }
            }
            if (minI != i) {
                Sorter.swap(arr, i, minI);
            }
        }
        return arr;
    }
}
