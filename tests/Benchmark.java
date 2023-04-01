import dog.silly.util.UnsafeArray;

import java.util.ArrayList;

public final @SuppressWarnings("all") class Benchmark {
    private static final int SIZE = 1000000;
    private static final int REPEATS = 5;
    private static long START, END, X;

    public static void main(String[] args) {
        for (int i = 0; i < REPEATS; i++) {
            benchmarkUnsafeArray(i + 1);
            benchmarkNativeArray(i + 1);
            benchmarkArrayList(i + 1);
        }

        // Prevent the compiler from optimizing the read tests.
        if (X == 1) System.out.println();
    }

    private static void benchmarkUnsafeArray(int label) {
        UnsafeArray<Object> array = new UnsafeArray<>(SIZE);
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        START = System.nanoTime();
        for (int i = 0; i < SIZE; i++)
            array.set(i, new Object());
        END = System.nanoTime();
        System.out.print("Write: " + (double) (END - START) / SIZE + " ns | ");

        START = System.nanoTime();
        for (Object o : array) X++;
        END = System.nanoTime();
        System.out.println("Read: " + (double) (END - START) / SIZE + " ns");

        array.delete();
        System.gc();
    }

    private static void benchmarkNativeArray(int label) {
        Object[] array = new Object[SIZE];
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        START = System.nanoTime();
        for (int i = 0; i < SIZE; i++)
            array[i] = new Object();
        END = System.nanoTime();
        System.out.print("Write: " + (double) (END - START) / SIZE + " ns | ");

        START = System.nanoTime();
        for (Object o : array) X++;
        END = System.nanoTime();
        System.out.println("Read: " + (double) (END - START) / SIZE + " ns");

        System.gc();
    }

    private static void benchmarkArrayList(int label) {
        ArrayList<Object> array = new ArrayList<>();
        System.out.println(label + ": " + array.getClass() + " (" + SIZE + " operations)");

        START = System.nanoTime();
        for (int i = 0; i < SIZE; i++)
            array.add(new Object());
        END = System.nanoTime();
        System.out.print("Write: " + (double) (END - START) / SIZE + " ns | ");

        START = System.nanoTime();
        for (Object o : array) X++;
        END = System.nanoTime();
        System.out.println("Read: " + (double) (END - START) / SIZE + " ns");

        System.gc();
    }
}