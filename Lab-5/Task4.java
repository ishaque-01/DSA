import java.util.Stack;

public class Task4 {

    public static String infixToPostfix(String expression) {

        String equation = "";

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char sign = expression.charAt(i);

            if (Character.isLetterOrDigit(sign)) {
                equation += sign;
            } else {
                while (!stack.isEmpty() && checkSign(sign) <= checkSign(stack.peek())) {
                    equation += stack.pop();
                }
                stack.push(sign);
            }
        }

        while (!stack.isEmpty()) {
            equation += stack.pop();
        }

        return equation;
    }

    private static int checkSign(char sign) {

        switch (sign) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }

    }

    public static void main(String[] args) {

        String equation = infixToPostfix("A+B*C+D");

        System.out.println("Postfix: " + equation);

    }
}
