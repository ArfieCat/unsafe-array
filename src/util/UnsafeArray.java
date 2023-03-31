package util;

import util.internal.UnsafeUtils;
import util.internal.stdlib;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.RandomAccess;

import static util.internal.stdlib.*;

/**
 * A memory-unsafe array implementation that can contain {@link Long#MAX_VALUE} {@code /} {@link stdlib#SIZEOF_PTR}
 * elements, or as many as possible before running out of memory.
 * <br> <br>
 * An {@code UnsafeArray} is similar to an {@code Object[]}. The default value is of an element is {@code null}, and the
 * element at an index can be written and read with {@link #get(long) get} and {@link #set(long, Object) set}. An
 * {@code UnsafeArray} can also be manually resized with {@link #resize(long) resize}, but is otherwise fixed-size.
 * <br> <br>
 * Memory allocated to an {@code UnsafeArray} can never be freed by the garbage collector. To prevent a memory leak, it
 * must be manually destroyed with {@link #delete() delete}.
 * <br> <br>
 * Attempting to access an index out of bounds or perform any operation on a destroyed {@code UnsafeArray} yields
 * undefined results. Accessing unallocated memory will crash the JVM with a segmentation fault.
 */
public @SuppressWarnings("all") class UnsafeArray<E> implements Iterable<E>, RandomAccess, Serializable {
    private transient long address;
    private long length;

    /**
     * Constructs a new array with all elements initialized to {@code null}.
     *
     * @param length The length of the array.
     * @throws OutOfMemoryError
     */
    public UnsafeArray(long length) {
        address = malloc(length * SIZEOF_PTR);
        this.length = length;
    }

    /**
     * Constructs a new array with the same length and elements as an existing array.
     *
     * @param array The array to copy.
     * @throws OutOfMemoryError
     */
    public UnsafeArray(UnsafeArray<E> array) {
        this(array.length);
        memcpy(address, array.address, array.length);
    }

    /**
     * Replaces the element of this array at a particular index. Note: Attempting to access an index out of bounds may
     * crash the JVM with a segmentation fault.
     *
     * @param index   The index of the element to replace.
     * @param element The element to replace it with.
     */
    public void set(long index, E element) {
        if (SIZEOF_PTR == 8) UnsafeUtils.getUnsafe().putLong(address + index * SIZEOF_PTR, x64u0026(element));
        else UnsafeUtils.getUnsafe().putInt(address + index * SIZEOF_PTR, x32u0026(element));
    }

    /**
     * Returns the element of this array at a particular index. Note: Attempting to access an index out of bounds may
     * crash the JVM with a segmentation fault.
     *
     * @param index The index of the element to return.
     * @return The element.
     */
    public E get(long index) {
        return (E) (SIZEOF_PTR == 8 ? x64u002A(UnsafeUtils.getUnsafe().getLong(address + index * SIZEOF_PTR))
                : x32u002A(UnsafeUtils.getUnsafe().getInt(address + index * SIZEOF_PTR)));
    }

    /**
     * Resizes this array, with any new elements initialized to {@code null}.
     *
     * @param length The new length of the array.
     * @throws OutOfMemoryError
     */
    public void resize(long length) {
        address = realloc(address, length * SIZEOF_PTR);
        this.length = length;
    }

    /**
     * Destroys this array and frees any memory allocated to it. Note: Attempting to perform any operation on this array
     * after destroying it may crash the JVM with a segmentation fault.
     */
    public void delete() {
        free(address);
    }

    /**
     * Returns the length of this array.
     *
     * @return The length.
     */
    public long length() {
        return length;
    }

    /**
     * Returns a string representation of this array, up to and excluding the first {@code null}, if applicable.
     *
     * @return The string.
     */
    public @Override String toString() {
        StringBuilder s = new StringBuilder();
        for (long i = 0; i < length; i++) {
            if (get(i) == null) return "[" + s.append("... (").append(length - i).append(" more)") + "]";
            s.append(get(i)).append(i == length - 1 ? "" : ", ");
        }
        return "[" + s + "]";
    }

    /**
     * Returns an iterator over the elements of this array.
     *
     * @return The iterator.
     */
    public @Override Iterator<E> iterator() {
        return new UnsafeArrayIterator();
    }

    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (long i = 0; i < length; i++) s.writeObject(get(i));
    }

    private void readObject(java.io.ObjectInputStream s) throws ClassNotFoundException, IOException {
        s.defaultReadObject();
        address = malloc(length);
        for (long i = 0; i < length; i++) set(i, (E) s.readObject());
    }

    private class UnsafeArrayIterator implements Iterator<E> {
        private long index = 0;

        public @Override boolean hasNext() {
            return index < length && get(index) != null;
        }

        public @Override E next() {
            return get(index++);
        }
    }
}