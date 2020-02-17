public class Recursion {
    static int fibonacciRecursive(int n){
        if (n <= 1) {
            return 1;
        } else {
            return fibonacciRecursive(n-1) + fibonacciRecursive(n-2);
        }
    }

    static void towersOfHanoi(int numDisks, String source, String dest, String auxilliary) {
        if (numDisks == 1) {
            System.out.printf("%s -> %s\n", source, dest);
        } else {
            towersOfHanoi(numDisks - 1, source, auxilliary, dest);
            System.out.printf("%s -> %s\n", source, dest);
            towersOfHanoi(numDisks - 1, auxilliary, dest, source);
        }
    }

    public static void main(String[] args) {
        towersOfHanoi(10, "A", "B", "C");
    }
}