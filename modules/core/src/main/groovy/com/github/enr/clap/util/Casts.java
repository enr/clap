package com.github.enr.clap.util;

import java.util.Map;

/**
 * Utility methods to cast objects in a single line of code.
 */
public class Casts {

    /**
     * Casts obj to the inferenced type T, failing with a ClassCastException if obj is null or not instanceable.
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    /**
     * Casts obj to the given type, failing with a ClassCastException if obj is null or not instanceable.
     */
    @SuppressWarnings("unchecked")
    public static <T> T castOrFail(Object obj, Class<T> type) {
        checkArgumentNotNull(type);
        if ((obj != null) && (type.isAssignableFrom(obj.getClass()))) {
            return (T) obj;
        }
        throw new ClassCastException(String.format("error casting %s to class %s", obj, type.getName()));
    }

    /**
     * Casts obj to the given type, returning null if obj is null or not instanceable.
     */
    @SuppressWarnings("unchecked")
    public static <T> T castOrNull(Object obj, Class<T> type) {
        checkArgumentNotNull(type);
        if ((obj != null) && (type.isAssignableFrom(obj.getClass()))) {
            return (T) obj;
        }
        return null;
    }

    /**
     * Returns, for the specified key, an object casted to the required class, or throws an IllegalArgumentException.
     * Useful for raw maps.
     * 
     */
    //@SuppressWarnings("unchecked")
    public static <T> T getFromMap(Map<?, ?> m, Object key, Class<T> type) {
        checkArgumentNotNull(m);
        checkArgumentNotNull(key);
        //checkArgumentNotNull(type);
        
        Object value = m.get(key);
        /*
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Incorrect type specified for header '" + key + "'. Expected [" + type
                    + "] but actual type is [" + value.getClass() + "]");
        }
        return (T) value;
        */
        return castOrNull(value, type);
    }
    
    private static void checkArgumentNotNull(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }

}
