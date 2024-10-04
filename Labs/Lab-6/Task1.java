public class Task1 {
    public static void main(String[] args) {

        int[] arr = { 5, 4, 3, 2, 1 };

        System.out.println("Unsorted Array:");

        for (int i : arr) {
            System.out.print(i + " ");
        }

        // selectionSort(arr);

        insertionSort(arr);

        System.out.println("\nSorted Array:");

        for (int i : arr) {
            System.out.print(i + " ");
        }

    }

    // private static void selectionSort(int[] arr) {

    //     for (int i = 0; i < arr.length - 1; i++) {
    //         for (int j = i + 1; j < arr.length; j++) {
    //             if (arr[j] < arr[i]) {
    //                 int temp = arr[i];
    //                 arr[i] = arr[j];
    //                 arr[j] = temp;
    //             }
    //         }
    //     }
    // }

    private static void insertionSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }
}