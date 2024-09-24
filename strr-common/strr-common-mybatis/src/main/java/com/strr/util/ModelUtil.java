package com.strr.util;

import com.strr.base.annotation.Column;
import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.exception.KeyNotFoundException;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实体类注解工具
 */
public class ModelUtil {
    private static final Pattern UNDERSCORE = Pattern.compile("_(\\S)");

    /**
     * 获取表名注解
     */
    public static Optional<Table> getTableOptional(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Table.class));
    }

    /**
     * 获取表名
     */
    public static String getTable(Class<?> clazz) {
        return getTableOptional(clazz).map(Table::value).orElse(toUnderscore(clazz.getSimpleName()));
    }

    /**
     * 获取字段名注解
     */
    public static Optional<Column> getColumnOptional(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class));
    }

    /**
     * 获取字段名
     */
    public static String getColumn(Field field) {
        return getColumnOptional(field).map(Column::value).filter(str -> str.length() > 0).orElse(toUnderscore(field.getName()));
    }

    /**
     * 模糊查询
     */
    public static boolean isFuzzy(Field field) {
        return getColumnOptional(field).map(Column::fuzzy).orElse(false);
    }

    /**
     * 是否主键
     */
    public static boolean isKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    /**
     * 是否有主键
     */
    public static boolean hasKey(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取主键
     */
    public static Field getKey(Field[] fields) throws KeyNotFoundException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new KeyNotFoundException();
    }

    /**
     * 驼峰转下划线
     */
    public static String toUnderscore(String camelCase) {
        return camelCase.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }

    /**
     * 下划线转驼峰
     */
    public static String toCamelCase(String underscore) {
        Matcher matcher = UNDERSCORE.matcher(underscore);
        return matcher.replaceAll(r -> r.group(1).toUpperCase(Locale.ROOT));
    }
}
