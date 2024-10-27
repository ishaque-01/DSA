public class Task1 {

    public static String decimalToBinary(int num) {
        if (num == 0)
            return "";

        String binary = decimalToBinary(num / 2) + Integer.toString(num % 2);
        return binary;
    }

    public static void main(String[] args) {

        System.out.println("Binary of 13 is: " + decimalToBinary(13));

    }
}
