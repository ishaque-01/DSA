public class Task3 {

    public static void mergeSort(int[] array) {
        int length = array.length;
        if (length == 1)
            return;

        int mid = length / 2;
        int[] left = new int[mid];
        int[] right = new int[length - mid];

        int j = 0;
        for (int i = 0; i < length; i++) {
            if (i < mid) {
                left[i] = array[i];
            } else {
                right[j] = array[i];
                j++;
            }
        }

        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);

    }

    public static void merge(int[] array, int[] left, int[] right) {
        int leftSize = left.length;
        int rightSize = right.length;

        int l = 0, r = 0, i = 0;

        while (l < leftSize && r < rightSize) {
            if (left[l] <= right[r]) {
                array[i++] = left[l++];
            } else {
                array[i++] = right[r++];
            }
        }

        while (l < leftSize) {
            array[i++] = left[l++];
        }

        while (r < rightSize) {
            array[i++] = right[r++];
        }
    }

    public static void quickSort(int[] array, int low, int high) {
        if (high <= low)
            return;

        int pivot = partition(array, low, high);

        quickSort(array, low, pivot - 1);
        quickSort(array, pivot + 1, high);
    }

    private static int partition(int[] array, int low, int high) {

        int pivotIdx = array[high];

        int i = low - 1;

        for (int j = low; j <= high; j++) {
            if (array[j] < pivotIdx) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        i++;
        int temp = array[i];
        array[i] = array[high];
        array[high] = temp;

        return i;
    }

    public static void main(String[] args) {

        int[] array = { 2, 1, 9, 8, 30, 20 };

        System.out.println("Unsorted Array");
        for (int i : array) {
            System.out.print(i + " ");
        }

        // mergeSort(array);
        // System.out.println("\nSorted Array (Merge Sort)");
        // for (int i : array) {
        // System.out.print(i + " ");
        // }

        quickSort(array, 0, array.length - 1);
        System.out.println("\nSorted Array (Quick Sort)");
        for (int i : array) {
            System.out.print(i + " ");
        }

    }
}
