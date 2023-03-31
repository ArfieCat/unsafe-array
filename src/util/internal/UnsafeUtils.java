package util.internal;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final @SuppressWarnings("all") class UnsafeUtils {
    private static Unsafe theUnsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            theUnsafe = (Unsafe) f.get(null);
        } catch (ReflectiveOperationException e) { throw new Error("Could not access " + Unsafe.class); }
    }

    public static Unsafe getUnsafe() {
        return theUnsafe;
    }

    private UnsafeUtils() throws Error {
        throw new Error("Cannot instantiate " + getClass());
    }
}