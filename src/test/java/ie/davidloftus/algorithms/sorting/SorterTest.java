package ie.davidloftus.algorithms.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    @ParameterizedTest
    @ValueSource(classes = {SelectionSort.class, InsertionSort.class, MergeSort.class, QuickSort.class, QuickSortEnhanced.class})
    void sort(Class<? extends Sorter<Integer>> sorterClass) throws ReflectiveOperationException {
        Sorter<Integer> sorter = sorterClass.getConstructor().newInstance();

        List<Integer> arr = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        List<Integer> arrCopy = new ArrayList<>(arr);

        sorter.sort(arrCopy);

        assertEquals(arr, arrCopy);

        Collections.shuffle(arrCopy);
        arrCopy = sorter.sort(arrCopy);

        assertEquals(arr, arrCopy);
    }
}