package ie.davidloftus.algorithms;

public class Recursion {
    static int fibonacciRecursive(int n){
        if (n <= 1) {
            return 1;
        } else {
            return fibonacciRecursive(n-1) + fibonacciRecursive(n-2);
        }
    }

    static int fibonacciIterative(int n) {
        if (n<=1)
            return 1;

        int fib = 1;
        int prevFib =  1;

        for (int i = 2; i <= n; i++) {
            int temp = fib;
            fib = fib + prevFib;
            prevFib = temp;
        }
        return fib;
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

    public static long time(Runnable func) {
        long before = System.nanoTime();

        func.run();

        return System.nanoTime() - before;
    }

    private static int gAccum = 0;

    public static void main(String[] args) {
        towersOfHanoi(10, "A", "B", "C");


        System.out.println(fibonacciRecursive(30));
        System.out.println(fibonacciRecursive(0));

        for (int i = 0; i < 20; ++i) {
            int n = i;
            long nanos = time(() -> fibonacciRecursive(n));
            long nanos2 = time(() -> gAccum ^= fibonacciIterative(n));
            System.out.printf("%d,%d,%d\n", n, nanos,nanos2);
        }
    }
}