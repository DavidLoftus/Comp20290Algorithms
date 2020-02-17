public class TimeIt {

    private Runnable setup = null;
    private Runnable func;

    public TimeIt(Runnable func) {
        this.func = func;
    }

    public TimeIt(Runnable setup, Runnable func) {
        this.func = func;
        this.setup = setup;
    }

    public long run() {

        if (setup != null) {
            setup.run();
        }

        long before = System.nanoTime();

        func.run();

        long after = System.nanoTime();

        return after - before;
    }

    public long run(int niters) {
        if (setup == null) {
            long before = System.nanoTime();
            for (int i = 0; i < niters; ++i) {
                func.run();
            }
            long after = System.nanoTime();
            return after - before;
        } else {
            long sum = 0;
            for (int i = 0; i < niters; ++i) {
                sum += run();
            }
            return sum;
        }
    }

}
