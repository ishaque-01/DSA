import java.util.Scanner;
import java.util.Stack;

public class Task3 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Stack<String> stack = new Stack<>();
        Stack<String> undoStack = new Stack<>();

        boolean cond = true;

        while (cond) {
            System.out.print("Enter Choice (404 for Exit): ");
            int choice = scan.nextInt();

            scan.nextLine();

            if (choice == 404) {
                break;
            } else if (choice == 1) {

                System.out.print("Input: ");
                String input = scan.nextLine();
                stack.push(input);

            } else if (choice == 2) {
                if (!stack.isEmpty()) {
                    undoStack.push(stack.pop());
                    System.out.println("Output: Undo Successful");
                }
            } else if (choice == 3) {
                if (!undoStack.isEmpty()) {
                    stack.push(undoStack.pop());
                    System.out.println("Output: Redo Successful");
                }
            } else if (choice == 4) {

                if (stack.isEmpty()) {
                    System.out.println("Nothing To Show");
                } else {

                    Stack<String> tempStack = new Stack<>();
                    while (!stack.isEmpty()) {
                        String element = stack.pop();
                        System.out.print(element + " ");
                        tempStack.push(element);
                    }

                    while (!tempStack.isEmpty()) {
                        stack.push(tempStack.pop());
                    }
                    System.out.println();

                }
            }
        }
        scan.close();
    }
}
