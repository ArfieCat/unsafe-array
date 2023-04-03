package dog.silly.util.internal;

/**
 * Provides static methods for unsafe memory management.
 */
public final @SuppressWarnings("all") class stdlib {
    /**
     * The number of bytes per pointer (4 for 32-bit JVM architectures and 8 for 64-bit).
     */
    public static final long SIZEOF_PTR = UnsafeUtils.getUnsafe().arrayIndexScale(Object[].class);

    private static final Object[] HEAP = new Object[1];
    private static final long OFFSET = UnsafeUtils.getUnsafe().arrayBaseOffset(Object[].class);

    /**
     * 32-bit implementation of the reference operator, effectively {@code &object}.
     *
     * @param object The object to reference.
     * @return The pointer.
     */
    public static int x32u0026(Object object) {
        HEAP[0] = object;
        return UnsafeUtils.getUnsafe().getInt(HEAP, OFFSET);
    }

    /**
     * 64-bit implementation of the reference operator, effectively {@code &object}.
     *
     * @param object The object to reference.
     * @return The pointer.
     */
    public static long x64u0026(Object object) {
        HEAP[0] = object;
        return UnsafeUtils.getUnsafe().getLong(HEAP, OFFSET);
    }

    /**
     * 32-bit implementation of the dereference operator, effectively {@code *pointer}.
     *
     * @param pointer The pointer to dereference.
     * @return The object.
     */
    public static Object x32u002A(int pointer) {
        UnsafeUtils.getUnsafe().putInt(HEAP, OFFSET, pointer);
        return HEAP[0];
    }

    /**
     * 64-bit implementation of the dereference operator, effectively {@code *pointer}.
     *
     * @param pointer The pointer to dereference.
     * @return The object.
     */
    public static Object x64u002A(long pointer) {
        UnsafeUtils.getUnsafe().putLong(HEAP, OFFSET, pointer);
        return HEAP[0];
    }

    public static long malloc(long size) {
        return UnsafeUtils.getUnsafe().allocateMemory(size);
    }

    public static long realloc(long address, long size) {
        return UnsafeUtils.getUnsafe().reallocateMemory(address, size);
    }

    public static void free(long address) {
        UnsafeUtils.getUnsafe().freeMemory(address);
    }

    public static void memcpy(long dest, long src, long size) {
        UnsafeUtils.getUnsafe().copyMemory(src, dest, size);
    }

    private stdlib() {
        throw new Error("Cannot instantiate " + getClass());
    }
}