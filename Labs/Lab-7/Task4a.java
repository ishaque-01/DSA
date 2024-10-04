import java.util.Scanner;

public class Task4a {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Number of Terms: ");
        int n = scan.nextInt();
        int num1 = 0, num2 = 1;
        scan.close();

        System.out.println("Fibonacci Series of n terms(Iteration): ");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            int sum = num1 + num2;
            System.out.print(num1 + " ");
            num1 = num2;
            num2 = sum;
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("\nTotal Time: " + totalTime + "ms");
    }
}
