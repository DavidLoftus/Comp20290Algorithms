package ie.davidloftus.algorithms.sorting;

import java.util.List;

public class StalinSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public boolean isInPlace() {
        return true;
    }

    @Override
    public List<T> sort(List<T> arr) {
        int writeIdx = 1;
        for (int i = 1; i < arr.size(); ++i) {
            if (arr.get(i).compareTo(arr.get(writeIdx - 1)) >= 0) {
                arr.set(writeIdx++, arr.get(i));
            }
        }

        while (arr.size() > writeIdx) {
            arr.remove(writeIdx);
        }

        return arr;
    }
}
