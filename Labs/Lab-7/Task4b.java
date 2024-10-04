import java.util.Scanner;

public class Task4b {

    public static void fibonacci(int n, int num1, int num2) {
        if (n == 0)
            return;

        System.out.print(num1 + " ");
        fibonacci(n - 1, num2, num1 + num2);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Number of Terms: ");
        int n = scan.nextInt();
        int num1 = 0, num2 = 1;

        scan.close();

        System.out.println("Fibonacci Series of n terms(Resursion): ");

        long startTime = System.currentTimeMillis();

        fibonacci(n, num1, num2);
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total Time: " + totalTime + "ms");

    }
}
