package ie.davidloftus.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSortEnhanced<T extends Comparable<T>> extends MergeSort<T> {
    @Override
    public List<T> sort(List<T> arr) {
        int n = arr.size();
        if (n <= 1) {
            return arr;
        } else if (n <= 5) {
            List<T> newList = new ArrayList<>(arr);
            Sorter<T> sorter = new InsertionSort<>();
            return sorter.sort(newList);
        }

        List<T> left = sort(arr.subList(0, n/2));
        List<T> right = sort(arr.subList(n/2, n));

        return merge(left, right);
    }

    protected List<T> merge(List<T> left, List<T> right) {
        if (left.isEmpty()) {
            return right;
        } else if (right.isEmpty()) {
            return left;
        } else if (left.get(left.size()-1).compareTo(right.get(0)) <= 0) {
            List<T> arr = new ArrayList<>();
            arr.addAll(left);
            arr.addAll(right);
            return arr;
        }

        return super.merge(left, right);
    }
}
