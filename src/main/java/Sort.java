import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Sort {

    private static void swap(int[] arr, int i, int j) {
        int val = arr[i];
        arr[i] = arr[j];
        arr[j] = val;
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

    public static int stalinSort(int[] arr) {
        int writeIdx = 1;
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] >= arr[writeIdx - 1]) {
                arr[writeIdx++] = arr[i];
            }
        }
        return writeIdx;
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

        private void generateArray(int n) {
            if (arr == null || arr.length != n) {
                arr = new int[n];
                for (int i = 0; i < n; ++i) {
                    arr[i] = i;
                }
            }
            shuffle(arr);
        }
    }

    public static void main(String[] args) {

        ArrayContainer arr = new ArrayContainer();

        int n = 10000;

        arr.generateArray(n);
        System.out.println(stalinSort(arr.arr));
        System.out.println(Arrays.toString(arr.arr));

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

        System.out.println(selectionTimer.run(100) / 1000000.0);
        System.out.println(insertionTimer.run(100) / 1000000.0);
        System.out.println(stalinTimer.run(100) / 1000000.0);
    }

}
