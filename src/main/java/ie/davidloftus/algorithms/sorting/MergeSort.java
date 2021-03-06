package ie.davidloftus.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public boolean isInPlace() {
        return false;
    }

    @Override
    public List<T> sort(List<T> arr) {
        int n = arr.size();
        if (n <= 1) {
            return arr;
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
        }

        List<T> list = new ArrayList<>();
        int leftIdx = 0, rightIdx = 0;
        while (leftIdx < left.size() && rightIdx < right.size()) {
            T leftVal = left.get(leftIdx);
            T rightVal = right.get(rightIdx);
            if (leftVal.compareTo(rightVal) <= 0) {
                list.add(leftVal);
                leftIdx++;
            } else {
                list.add(rightVal);
                rightIdx++;
            }
        }
        list.addAll(left.subList(leftIdx, left.size()));
        list.addAll(right.subList(rightIdx, right.size()));
        return list;
    }
}
