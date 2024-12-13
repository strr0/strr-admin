package com.strr.base.util;

/**
 * 数组工具类
 */
public class ArrayUtil {
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return null != array && array.length != 0;
    }
}
