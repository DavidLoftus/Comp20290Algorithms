package ie.davidloftus.algorithms.bench;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimeIt<T> {

    private Supplier<T> setup;
    private Consumer<T> func;

    public TimeIt(Supplier<T> setup, Consumer<T> func) {
        this.func = func;
        this.setup = setup;
    }

    public static <T> long run(long minNano, Supplier<T> setup, Consumer<T> func) {
        TimeIt<T> timer = new TimeIt<>(setup, func);
        return timer.run(minNano);
    }

    public static long run(long minNano, Runnable setup, Runnable func) {
        return run(minNano, () -> {setup.run(); return null;}, __ -> func.run());
    }

    public long run() {

        T setupVar = setup.get();

        long before = System.nanoTime();

        func.accept(setupVar);

        long after = System.nanoTime();

        return after - before;
    }

    public long run(long minNano) {
        long sum = 0;
        int iters = 0;
        while(sum < minNano) {
            sum += run();
            iters++;
        }
        return sum / iters;
    }

}
