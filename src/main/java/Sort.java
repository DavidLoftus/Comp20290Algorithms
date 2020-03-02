import java.util.*;

public class Sort {

    private static <T> void swap(List<T> arr, int i, int j) {
        T val = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, val);
    }

    public static <T extends Comparable<T>> List<T> selectionSort(List<T> arr) {
        for (int i = 0; i < arr.size()-1; ++i) {
            int minI = i;
            for (int j = i+1; j < arr.size(); ++j) {
                if (arr.get(j).compareTo(arr.get(minI)) < 0) {
                    minI = j;
                }
            }
            if (minI != i) {
                swap(arr, i, minI);
            }
        }
        return arr;
    }

    public static <T extends Comparable<T>> List<T> insertionSort(List<T> list) {
        for (int i = 1; i < list.size(); ++i) {
            for (int j = i; j > 0 && list.get(j-1).compareTo(list.get(j)) > 0; --j) {
                swap(list, j, j-1);
            }
        }
        return list;
    }

    public static <T extends Comparable<T>> List<T> stalinSort(List<T> arr) {
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

    public static List<Integer> merge(List<Integer> left, List<Integer> right) {
        if (left.isEmpty()) {
            return right;
        } else if (right.isEmpty()) {
            return left;
        } else if (left.get(left.size()-1) <= right.get(0)) {
            List<Integer> arr = new ArrayList<>();
            arr.addAll(left);
            arr.addAll(right);
            return arr;
        }

        List<Integer> list = new ArrayList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.get(left.size()-1) <= right.get(right.size()-1)) {
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

    public static List<Integer> mergeSort(List<Integer> list) {
        int n = list.size();
        if (n <= 1) {
            return list;
        } else if (n <= 5) {
            List<Integer> newList = new ArrayList<>(list);
            return insertionSort(newList);
        }

        List<Integer> left = mergeSort(list.subList(0, n/2));
        List<Integer> right = mergeSort(list.subList(n/2, n));

        return merge(left, right);
    }


    static class ArrayContainer {
        List<Integer> list = null;

        private void generateList(int n) {
            if (list == null || list.size() != n) {
                list = new ArrayList<>(n);
                for (int i = 0; i < n; ++i) {
                    list.add(i);
                }
            }
            Collections.shuffle(list);
        }
    }

    public static <T extends Comparable<T>> List<T> quickSort(List<T> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int pivotIdx = partition(arr);
        quickSort(arr.subList(0, pivotIdx));
        quickSort(arr.subList(pivotIdx+1, arr.size()));

        return arr;
    }

    private static <T extends Comparable<T>> int partition(List<T> arr) {
        int i = -1;

        T pivot = arr.get(arr.size()-1);

        for (int j = 0; j < arr.size(); ++j) {
            if (arr.get(j).compareTo(pivot) < 0) {
                ++i;
                // Swap arr[i] and arr[j]
                swap(arr, i, j);
            }
        }

        // Swap arr[i+1] and arr[head]
        swap(arr, i+1, arr.size()-1);

        return i+1;
    }

    public static void main(String[] args) {

        ArrayContainer arr = new ArrayContainer();

        arr.generateList(100);
        System.out.println(stalinSort(arr.list));
        System.out.println(arr.list);

        for (int i = 0; i < 1000; ++i) {
            selectionSort(arr.list);
            insertionSort(arr.list);
            stalinSort(arr.list);
            mergeSort(arr.list);
            quickSort(arr.list);
        }

        System.out.print("n\tselection\tinsertion\tstalin\tmerge\tquick\n");
        for (int i = 100; i <= 2000; i += 100) {
            int n = i;
            TimeIt selectionTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> selectionSort(arr.list)
            );

            TimeIt insertionTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> insertionSort(arr.list)
            );

            TimeIt stalinTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> stalinSort(arr.list)
            );

            TimeIt mergeTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> mergeSort(arr.list)
            );

            TimeIt quickTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> mergeSort(arr.list)
            );

            long selectionTime = selectionTimer.run(100);
            long insertionTime = insertionTimer.run(100);
            long stalinTime = stalinTimer.run(100);
            long mergeTime = mergeTimer.run(100);
            long quickTime = mergeTimer.run(100);

            System.out.printf("%d\t%f\t%f\t%f\t%f\t%f\n", i,
                    selectionTime / 1000000.0,
                    insertionTime / 1000000.0,
                    stalinTime / 1000000.0,
                    mergeTime / 1000000.0,
                    quickTime / 1000000.0);
        }
    }

}
