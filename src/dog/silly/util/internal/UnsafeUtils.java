package dog.silly.util.internal;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Provides convenient access to {@link Unsafe}.
 */
public final @SuppressWarnings("all") class UnsafeUtils {
    private static Unsafe theUnsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            theUnsafe = (Unsafe) f.get(null);
        } catch (ReflectiveOperationException e) {
            throw new Error("Could not access " + Unsafe.class);
        }
    }

    /**
     * Returns the single instance of {@link Unsafe}.
     *
     * @return The {@code Unsafe}.
     */
    public static Unsafe getUnsafe() {
        return theUnsafe;
    }

    private UnsafeUtils() throws Error {
        throw new Error("Cannot instantiate " + getClass());
    }
}