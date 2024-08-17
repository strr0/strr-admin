package com.strr.util;

import com.strr.base.annotation.Column;
import com.strr.base.annotation.Id;
import com.strr.base.annotation.Table;
import com.strr.base.exception.KeyNotFoundException;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 实体类注解工具
 */
public class ModelUtil {
    /**
     * 获取表名注解
     */
    public static Optional<Table> getTableOpt(Class<?> clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Table.class));
    }

    /**
     * 获取表名
     */
    public static String getTable(Class<?> clazz) {
        return getTableOpt(clazz).map(Table::value).orElse(clazz.getSimpleName());
    }

    /**
     * 获取字段名注解
     */
    public static Optional<Column> getColumnOpt(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class));
    }

    /**
     * 获取字段名
     */
    public static String getColumn(Field field) {
        return getColumnOpt(field).map(Column::value).filter(str -> str.length() > 0).orElse(field.getName());
    }

    /**
     * 模糊查询
     */
    public static boolean isFuzzy(Field field) {
        return getColumnOpt(field).map(Column::fuzzy).orElse(false);
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
}
