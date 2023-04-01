import dog.silly.util.UnsafeArray;

public class Example {
    private static final long SIZE = (long) Integer.MAX_VALUE + 1;

    public static void main(String[] args) {
        // Unsafe arrays can be longer than native arrays or Collections.
        UnsafeArray<Object> array = new UnsafeArray<>(SIZE);

        // This will take a while... or you might run out of memory.
        for (long i = 0; i < array.length(); i++)
            array.set(i, new Object());

        System.out.println(array);

        // Destroy the array once it is no longer needed.
        array.delete();
    }
}