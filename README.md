# unsafe array :point_up::left_right_arrow:

A memory-unsafe array implementation that can contain up to `Long.MAX_VALUE` elements, or as many as possible before encountering an `OutOfMemoryError`.

This array behaves similarly to a native `Object[]` array. The value at an index can be accessed and mutated with `set(long, Object)` and `get(long)`. Otherwise, the default value is `null`.

It is possible to access an index out of bounds. However, accessing unallocated memory is likely to crash the JVM with a segmentation fault. It may be useful to first resize the array with `resize(long)`.

Memory allocated to this array cannot be freed by the garbage collector. To prevent memory leaks, this array must be manually destroyed with `delete()`.

## Example

```java


public class Main {
    public static void util(String[] args) {
        Array<Object> array = new Array<>(Long.MAX_VALUE);

        for (long i = 0; i < array.length(); i++)
            array.set(i, new Object());

        System.out.println(array);

        array.delete();
    }
}
```