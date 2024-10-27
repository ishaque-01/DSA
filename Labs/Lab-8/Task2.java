public class Task2 {

    public static boolean checkPalindrome(String check) {
        if (check.length() == 0 || check.length() == 1) {
            return true;
        }

        if (check.charAt(0) == check.charAt(check.length() - 1)) {
            return checkPalindrome(check.substring(1, check.length() - 1));
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("RACECARS: " + checkPalindrome("racecars"));
    }

}