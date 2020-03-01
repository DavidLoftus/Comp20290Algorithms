import java.util.*;
import java.util.stream.Collectors;

public class Sort {

    private static void swap(int[] arr, int i, int j) {
        int val = arr[i];
        arr[i] = arr[j];
        arr[j] = val;
    }

    private static void swap(List<Integer> arr, int i, int j) {
        int val = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, val);
    }

    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length-1; ++i) {
            int minI = i;
            for (int j = i+1; j < arr.length; ++j) {
                if (arr[j] < arr[minI]) {
                    minI = j;
                }
            }
            if (minI != i) {
                swap(arr, i, minI);
            }
        }
        return arr;
    }

    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; ++i) {
            for (int j = i; j > 0 && arr[j-1] > arr[j]; --j) {
                swap(arr, j, j-1);
            }
        }
        return arr;
    }

    public static List<Integer> insertionSort(List<Integer> list) {
        for (int i = 1; i < list.size(); ++i) {
            for (int j = i; j > 0 && list.get(j-1) > list.get(j); --j) {
                swap(list, j, j-1);
            }
        }
        return list;
    }

    public static int stalinSort(int[] arr) {
        int writeIdx = 1;
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] >= arr[writeIdx - 1]) {
                arr[writeIdx++] = arr[i];
            }
        }
        return writeIdx;
    }

    public static List<Integer> merge(List<Integer> left, List<Integer> right) {
        if (left.isEmpty()) {
            return right;
        } else if (right.isEmpty()) {
            return left;
        } else if (left.get(left.size()-1) <= right.get(0)) {
            left.addAll(right);
            return left;
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
//            List<Integer> newList = new ArrayList<>(list.size());
//            newList.addAll(list);
//            return insertionSort(newList);
        }

        List<Integer> left = mergeSort(list.subList(0, n/2));
        List<Integer> right = mergeSort(list.subList(n/2, n));

        return merge(left, right);
    }

    private static int[] shuffle(int[] arr) {
        Random rand = new Random();
        for (int i=0; i<arr.length; i++) {
            int randomPosition = rand.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomPosition];
            arr[randomPosition] = temp;
        }
        return arr;
    }


    static class ArrayContainer {

        int[] arr = null;
        List<Integer> list = null;

        private void generateArray(int n) {
            if (arr == null || arr.length != n) {
                arr = new int[n];
                for (int i = 0; i < n; ++i) {
                    arr[i] = i;
                }
            }
            shuffle(arr);
        }

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

    public static void main(String[] args) {

        ArrayContainer arr = new ArrayContainer();

        arr.generateArray(100);
        arr.generateList(100);
        System.out.println(stalinSort(arr.arr));
        System.out.println(Arrays.toString(arr.arr));

        for (int i = 0; i < 1000; ++i) {
            selectionSort(arr.arr);
            insertionSort(arr.arr);
            stalinSort(arr.arr);
            mergeSort(arr.list);
        }

        System.out.printf("n\tselection\tinsertion\tstalin\tmerge\n");
        for (int i = 100; i <= 2000; i += 100) {
            int n = i;
            TimeIt selectionTimer = new TimeIt(
                    () -> arr.generateArray(n),
                    () -> selectionSort(arr.arr)
            );

            TimeIt insertionTimer = new TimeIt(
                    () -> arr.generateArray(n),
                    () -> insertionSort(arr.arr)
            );

            TimeIt stalinTimer = new TimeIt(
                    () -> arr.generateArray(n),
                    () -> stalinSort(arr.arr)
            );

            TimeIt mergeTimer = new TimeIt(
                    () -> arr.generateList(n),
                    () -> mergeSort(arr.list)
            );

            long selectionTime = selectionTimer.run(100);
            long insertionTime = insertionTimer.run(100);
            long stalinTime = stalinTimer.run(100);
            long mergeTime = mergeTimer.run(100);

            System.out.printf("%d\t%f\t%f\t%f\t%f\n", i,
                    selectionTime / 1000000.0,
                    insertionTime / 1000000.0,
                    stalinTime / 1000000.0,
                    mergeTime / 1000000.0);
        }
    }

}
