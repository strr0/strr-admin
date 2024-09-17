package com.strr.data.util;

import java.util.Arrays;
import java.util.List;

/**
 * 关键字工具类
 */
public class KeywordUtil {
    private static final List<String> KEYWORD = Arrays.asList("order");

    public static String getName(String name) {
        if (KEYWORD.contains(name)) {
            return String.format("`%s`", name);
        }
        return name;
    }
}
