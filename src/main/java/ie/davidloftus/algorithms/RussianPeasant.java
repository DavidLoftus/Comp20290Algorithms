package ie.davidloftus.algorithms;

public class RussianPeasant {

    public static long russianMultiply(int a, int b) {
        long sum = 0;
        long longB = b;
        while (a != 0) {
            if ((a & 1) != 0) {
                sum += longB;
            }
            longB <<= 1;
            a >>= 1;
        }
        return sum;
    }

    public static long multiply(int a, int b) {
        return (long) a * (long) b;
    }

    public static long time(Runnable func) {
        long before = System.nanoTime();

        func.run();

        return System.nanoTime() - before;
    }

    // Global accumulation variable to prevent JIT compiler optimizing out return values.
    private static long gAccum = 0;
    public static void main(String[] args) {

        for (int i = 0; i < 10000; ++i) {
            long a = russianMultiply(i<<16,i), b = multiply(i<<16,i);
            if (a != b) {
                throw new RuntimeException("i: " + i);
            }
            gAccum ^= a ^ b;
        }

        long russianMultiplyNanos = time(() -> {
            long accum = 0;
            for (int i = 0; i < 10000; ++i) {
                int a = i<<16, b = i << 8;
                accum ^= russianMultiply(a,b);
            }
            gAccum ^= accum;
        });

        System.out.printf("russianMultiply takes %.3fns on average\n", russianMultiplyNanos / 10000.0);

        long multiplyNanos = time(() -> {
            long accum = 0;
            for (int i = 0; i < 100000; ++i) {
                int a = i<<16, b = i << 8;
                accum ^= a*b;
            }
            gAccum ^= accum;
        });

        System.out.printf("multiply takes %.3fns on average\n", multiplyNanos / 100000.0);

        for (int i = 0; i < 31; ++i) {
            int a = 1 << i;
            int b = 1 << i;

            long nanos = time(() -> {
                long accum = 0;
                for (int k = 0; k < 10000; ++k) {
                    accum ^= russianMultiply(a,b);
                }
                gAccum ^= accum;
            });

            System.out.printf("%d,%.3f\n", i, nanos / 10000.0);
        }

        System.out.println(gAccum);

    }

}
