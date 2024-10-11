package com.strr.util;

/**
 * 字符串工具类
 */
public class StringUtil {
    public static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!CharUtil.isBlankChar(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean containsAny(CharSequence str, CharSequence... testStrs) {
        return null != getContainsStr(str, testStrs);
    }

    public static String getContainsStr(CharSequence str, CharSequence... testStrs) {
        if (!isEmpty(str) && !ArrayUtil.isEmpty(testStrs)) {
            for(CharSequence checkStr : testStrs) {
                if (null != checkStr && str.toString().contains(checkStr)) {
                    return checkStr.toString();
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
