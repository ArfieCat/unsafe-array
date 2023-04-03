import dog.silly.util.UnsafeArray;

import java.util.ArrayList;

public final @SuppressWarnings("all") class Benchmark {
    private static final int SIZE = 1000000;
    private static final int REPEATS = 5;
    private static final Object O = new Object();

    private static long start, end, x;

    public static void main(String[] args) {
        for (int i = 0; i < REPEATS; i++) {
            benchmarkUnsafeArray(i + 1);
            benchmarkNativeArray(i + 1);
            benchmarkArrayList(i + 1);

            System.out.println();
        }

        // Prevent the compiler from optimizing the read tests.
        if (x == 1) System.out.println();
    }

    private static void benchmarkUnsafeArray(int label) {
        UnsafeArray<Object> array = new UnsafeArray<>(SIZE);
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        start = System.nanoTime();
        for (int i = 0; i < SIZE; i++) array.set(i, O);
        end = System.nanoTime();
        System.out.print("Write: " + (double) (end - start) / SIZE + " ns | ");

        start = System.nanoTime();
        for (Object o : array) x++;
        end = System.nanoTime();
        System.out.println("Read: " + (double) (end - start) / SIZE + " ns");

        array.delete();
        System.gc();
    }

    private static void benchmarkNativeArray(int label) {
        Object[] array = new Object[SIZE];
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        start = System.nanoTime();
        for (int i = 0; i < SIZE; i++) array[i] = O;
        end = System.nanoTime();
        System.out.print("Write: " + (double) (end - start) / SIZE + " ns | ");

        start = System.nanoTime();
        for (Object o : array) x++;
        end = System.nanoTime();
        System.out.println("Read: " + (double) (end - start) / SIZE + " ns");

        System.gc();
    }

    private static void benchmarkArrayList(int label) {
        ArrayList<Object> array = new ArrayList<>();
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        start = System.nanoTime();
        for (int i = 0; i < SIZE; i++) array.add(O);
        end = System.nanoTime();
        System.out.print("Write: " + (double) (end - start) / SIZE + " ns | ");

        start = System.nanoTime();
        for (Object o : array) x++;
        end = System.nanoTime();
        System.out.println("Read: " + (double) (end - start) / SIZE + " ns");

        System.gc();
    }
}