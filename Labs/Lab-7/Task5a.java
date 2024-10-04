import java.util.Scanner;

public class Task5a {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Number You Want Factorial of: ");
        int n = scan.nextInt();
        scan.close();

        long startTime = System.currentTimeMillis();
        int fact = 1;
        
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Factorial of " + n + " is: " + fact);
    
        System.out.println("Total time: " + totalTime + "ms");
    
    }
}
