package com.strr.mybatis.interceptor.page;

/**
 * 方言
 */
public interface IDialect {
    String getPageSql(String sql, int page, int size);
}
