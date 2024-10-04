import java.util.Scanner;

public class Task1 {

    public static void printInAscending(int n) {
        if (n == 0)
            return;
        printInAscending(n - 1);
        System.out.print(n + " ");
    }

    public static void printInDescending(int n) {
        if (n == 0)
            return;
        System.out.print(n + " ");
        printInDescending(n - 1);
    }

    

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Number to print from 1 to N: ");
        int n = scan.nextInt();

        System.out.println("Ascending Order");
        printInAscending(n);

        System.out.println("\nDescending Order");
        printInDescending(n);
        System.out.println();

        scan.close();
    }
}