package sorting;

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
        } else if (n <= 5) {
            List<T> newList = new ArrayList<>(arr);
            Sorter<T> sorter = new InsertionSort<>();
            return sorter.sort(newList);
        }

        List<T> left = sort(arr.subList(0, n/2));
        List<T> right = sort(arr.subList(n/2, n));

        return merge(left, right);
    }

    private List<T> merge(List<T> left, List<T> right) {
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

        List<T> list = new ArrayList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.get(left.size()-1).compareTo(right.get(right.size()-1)) <= 0) {
                list.add(left.remove(left.size()-1));
            } else {
                list.add(right.remove(right.size()-1));
            }
        }
        while (!left.isEmpty()) {
            list.add(left.remove(left.size()-1));
        }
        while (!right.isEmpty()) {
            list.add(right.remove(right.size()-1));
        }
        return list;
    }
}
