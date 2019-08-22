package utils.verify;

import java.util.List;

class TestMethodErrorBuffer {
    // thread safe while running tests in parallel
    private static ThreadLocal<List<Throwable>> testErrorBuffer = new ThreadLocal<>();

    static List<Throwable> get() {
        return testErrorBuffer.get();
    }

    static void set(List<Throwable> errorBuffer) {
        testErrorBuffer.set(errorBuffer);
    }

    static void remove() {
        testErrorBuffer.remove();
    }
}
