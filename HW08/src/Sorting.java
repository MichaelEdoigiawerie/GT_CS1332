import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Michael Edoigiawerie
 * @version 1.0
 * @userid medoigiawerie3
 * @GTID 903626849
 *
 * Collaborators: None
 *
 * Resources: None
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or the comparator passed in is null");
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                swap(arr, i, i - 1);
                i--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or the comparator passed in is null");
        }
        int startInd = 0;
        int endInd = arr.length - 1;
        while (endInd > 0) {
            int lastSwapped = 0;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    lastSwapped = i;
                }
            }
            endInd = lastSwapped;
            for (int i = endInd; i > startInd; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    swap(arr, i - 1, i);
                    lastSwapped = i;
                }
            }
            startInd = lastSwapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or the comparator passed in is null");
        }
        if (arr.length < 2) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[midIndex];
        if (length % 2 != 0) {
            right = (T[]) new Object[midIndex + 1];
        }
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[midIndex + i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * A private method used to swap two elements in an array.
     * @param arr The array passed in.
     * @param curr The current index of the element.
     * @param target The index of the desired element to swap.
     * @param <T> The data type of the element.
     */
    private static <T> void swap(T[] arr, int curr, int target) {
        T temp = arr[curr];
        arr[curr] = arr[target];
        arr[target] = temp;
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array/comparator/rand passed in is null");
        }
        rQuickSort(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * This is a private recursive helper method for the above sorting algorithm.
     * @param arr The array that must be sorted.
     * @param comparator The comparator object used to compare the data in the array.
     * @param rand The Random object used to select pivots.
     * @param start The index to start from when choosing a pivot.
     * @param end The index to end when choosing a pivot.
     * @param <T> The type of data to sort.
     */
    private static <T> void rQuickSort(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        swap(arr, start, pivotIdx);
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        rQuickSort(arr, comparator, rand, start, j - 1);
        rQuickSort(arr, comparator, rand, j + 1, end);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array passed in is null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        int max = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (max < Math.abs(arr[i])) {
                max = Math.abs(arr[i]);
            }
        }
        int k = 1;
        while (max != 0) {
            max /= 10;
            k++;
        }
        int pow = 1;
        for (int iteration = 0; iteration < k; iteration++) {
            for (int i = 0; i < arr.length; i++) {
                int addIndex = arr[i] / pow % 10 + 9;
                buckets[addIndex].add(arr[i]);
            }
            pow *= 10;
            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx++] = bucket.remove();
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null");
        }
        buildMinHeap(data);
        int heapSize = data.size() - 1;
        for (int i = heapSize; i > 0; i--) {
            swapData(data, i, 0);
            heapSize--;
            minHeapify(data, 0, heapSize);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(data);
        int[] sortedArray = new int[pq.size()];
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = pq.remove();
        }
        return sortedArray;
    }

    /**
     * This private method function builds a min heap using the data passed in.
     * @param data The list being passed in.
     */
    private static void buildMinHeap(List<Integer> data) {
        for (int i = data.size() / 2 - 1; i >= 0; i--) {
            minHeapify(data, i, data.size() - 1);
        }
    }

    /**
     * This private method is used to downheap from the desired parent index.
     * @param data The list passed in.
     * @param pIndex The parent index.
     * @param endIdx The desired final index.
     */
    private static void minHeapify(List<Integer> data, int pIndex, int endIdx) {
        int lChild = pIndex * 2 + 1;
        int rChild = pIndex * 2 + 2;
        int minIndex = pIndex;
        if (lChild <= endIdx && data.get(lChild) < data.get(pIndex)) {
            minIndex = lChild;
        }
        if (rChild <= endIdx && data.get(rChild) <  data.get(pIndex)) {
            minIndex = rChild;
        }
        if (minIndex != pIndex) {
            swapData(data, minIndex, pIndex);
            minHeapify(data, minIndex, endIdx);
        }
    }

    /**
     * A private method used to swap two elements in a list.
     * @param list The list passed in.
     * @param curr The current index of the element.
     * @param target The index of the desired element to swap.
     */
    private static void swapData(List<Integer> list, int curr, int target) {
        int temp = list.get(curr);
        list.set(curr, list.get(target));
        list.set(target, temp);
    }
}
