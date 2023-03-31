# Unsafe Array :point_up::left_right_arrow:

A memory-unsafe array implementation that can contain up to `Long.MAX_VALUE` elements, or as many as possible before 
running out of memory.

An unsafe array is similar to an `Object[]`. The default value is of an element is `null`, and the element at an index 
can be written and read with the `set()` and `get()` methods. An unsafe array can also be manually resized with the 
`resize()` method, but is otherwise fixed-size.

Memory allocated to an unsafe array can never be freed by the garbage collector. To prevent a memory leak, it must be 
manually destroyed with the `delete()` method.

Attempting to access an index out of bounds or perform any operation on a destroyed unsafe array yields undefined 
results. Accessing unallocated memory will crash the JVM with a segmentation fault.

## Example

```java
import dog.silly.util.UnsafeArray;

public class Main {
    public static void util(String[] args) {
        UnsafeArray<Object> unsafeArray = new UnsafeArray<>(Long.MAX_VALUE);

        for (long i = 0; i < array.length(); i++)
            unsafeArray.set(i, new Object());

        System.out.println(unsafeArray);

        array.delete();
    }
}
```