public class Task2 {

    public static void printArrayAscending(int[] arr, int length) {
        if (length < 0)
            return;

            printArrayAscending(arr, length-1);
            System.out.print(arr[length] + " ");
    }

    public static void printArrayDescending(int[] arr, int length) {
        if (length < 0)
            return;
            
            System.out.print(arr[length] + " ");
            printArrayDescending(arr, length-1);
    }

    public static void main(String[] args) {
        
        int[] arr = { 1, 2, 3, 4, 5 };

        System.out.println("Array in ascending order:");
        printArrayAscending(arr, arr.length-1);
        
        System.out.println("\nArray in descending order:");
        printArrayDescending(arr, arr.length-1);
        System.out.println();

    }
}
