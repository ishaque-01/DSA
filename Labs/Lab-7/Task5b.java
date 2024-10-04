import java.util.Scanner;

public class Task5b {

    public static int factorial(int n) {
        if (n == 1 || n == 0)
            return 1;

        return n * factorial(n - 1);
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Number You Want Factorial of: ");
        int n = scan.nextInt();
        scan.close();

        long startTime = System.currentTimeMillis();
        System.out.println("Factorial of " + n + " is: " + factorial(n));
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total time: " + totalTime + "ms");

    }
}
