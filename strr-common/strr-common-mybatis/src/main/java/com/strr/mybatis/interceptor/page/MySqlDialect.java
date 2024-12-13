package com.strr.mybatis.interceptor.page;

/**
 * MySql 方言
 */
public class MySqlDialect implements IDialect {
    @Override
    public String getPageSql(String sql, int page, int size) {
        return String.format("%s limit %d offset %d", sql, size, (page - 1) * size);
    }
}
