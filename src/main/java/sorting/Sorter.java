package sorting;

import java.util.List;

public interface Sorter<T extends Comparable<T>> {

    boolean isInPlace();
    List<T> sort(List<T> arr);

    static <T> void swap(List<T> arr, int i, int j) {
        T val = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, val);
    }

}
