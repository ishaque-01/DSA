public class Task2 {
    public static void main(String[] args) {

        int[] arr = { 11, 22, 21, 92, 100, 34, 1, 23 };

        System.out.println("Unsorted Array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }

        mergeSort(arr, arr.length);

        System.out.println("\nSorted Array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }

    }

    private static void mergeSort(int[] arr, int n) {

        for (int curr = 1; curr < n; curr = 2 * curr) {
            for (int leftSide = 0; leftSide < n - 1; leftSide += 2 * curr) {
                int mid = Math.min(leftSide + curr - 1, n - 1);
                int rightEnd = Math.min(leftSide + curr * 2 - 1, n - 1);

                merge(arr, leftSide, mid, rightEnd);
            }
        }
    }

    private static void merge(int[] arr, int leftSide, int mid, int rigthEnd) {

        int leftSize = mid - leftSide + 1;
        int rightSize = rigthEnd - mid;

        int[] left = new int[leftSize];
        int[] right = new int[rightSize];

        for (int i = 0; i < leftSize; i++)
            left[i] = arr[leftSide + i];
        for (int j = 0; j < rightSize; j++)
            right[j] = arr[mid + 1 + j];

        int l = 0, r = 0, i = leftSide;

        while (l < leftSize && r < rightSize) {
            if (left[l] < right[r]) {
                arr[i] = left[l];
                i++;
                l++;
            } else {
                arr[i] = right[r];
                i++;
                r++;
            }
        }

        while (l < leftSize) {
            arr[i] = left[l];
            i++;
            l++;
        }

        while (r < rightSize) {
            arr[i] = right[r];
            i++;
            r++;
        }
    }
}
