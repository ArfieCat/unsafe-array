package util.internal;

public final @SuppressWarnings("all") class stdlib {
    public static final long SIZEOF_PTR = UnsafeUtils.getUnsafe().arrayIndexScale(Object[].class);

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

    public static int x32u0026(Object object) {
        return UnsafeUtils.getUnsafe().getInt(new Object[]{object}, UnsafeUtils.getUnsafe().arrayBaseOffset(Object[].class));
    }

    public static long x64u0026(Object object) {
        return UnsafeUtils.getUnsafe().getLong(new Object[]{object}, UnsafeUtils.getUnsafe().arrayBaseOffset(Object[].class));
    }

    public static Object x32u002A(int pointer) {
        Object[] heap = new Object[1];
        UnsafeUtils.getUnsafe().putInt(heap, UnsafeUtils.getUnsafe().arrayBaseOffset(Object[].class), pointer);

        return heap[0];
    }

    public static Object x64u002A(long pointer) {
        Object[] heap = new Object[1];
        UnsafeUtils.getUnsafe().putLong(heap, UnsafeUtils.getUnsafe().arrayBaseOffset(Object[].class), pointer);

        return heap[0];
    }

    private stdlib() {
        throw new Error("Cannot instantiate " + getClass());
    }
}