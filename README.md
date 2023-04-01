# Unsafe Array :point_up::left_right_arrow:

A memory-unsafe array implementation that can contain `Long.MAX_VALUE / SIZEOF_PTR`<sup>[1]</sup> elements, or as many
as possible before running out of memory.

An unsafe array is similar to an `Object[]`. The default value is of an element is `null`, and the element at an index
can be written and read with the `set()` and `get()` methods. An unsafe array can also be manually resized with the
`resize()` method, but is otherwise fixed-size.

Memory allocated to an unsafe array can never be freed by the garbage collector. To prevent a memory leak, it must be
manually destroyed with the `delete()` method.

Attempting to access an index out of bounds or perform any operation on a destroyed unsafe array yields undefined
results. Accessing unallocated memory will crash the JVM with a segmentation fault.

<sup>[1]</sup> `SIZEOF_PTR` is 4 for 32-bit JVM architectures and 8 for 64-bit.

## Example

```java
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
```