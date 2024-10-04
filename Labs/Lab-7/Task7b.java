import java.util.Random;
import java.util.Scanner;

public class Task7b {
    public static void main(String[] args) {

        Random rand = new Random();

        int[] arr = new int[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.nextInt(1000);
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Number to find in array: ");
        int n = scan.nextInt();
        scan.close();


        long startTime = System.currentTimeMillis();
        
        System.out.println("(recursive) Element found(1) else (0): " + findElement(arr, n, 0));
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Total time: " + totalTime + "ms");


        for (int i : arr) {
            System.out.print(i + " ");
        }System.out.println();

    }

    private static int findElement(int[] arr, int n, int idx) {

        if (idx >= arr.length)
            return 0;

        if (arr[idx] == n)
            return 1;

        findElement(arr, n, idx+1);
        return 0;
    }
}
