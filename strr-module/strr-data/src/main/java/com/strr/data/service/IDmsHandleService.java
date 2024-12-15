package com.strr.data.service;

/**
 * 数据处理
 */
public interface IDmsHandleService {
    /**
     * 注册
     */
    void register(Long id) throws Exception;

    /**
     * 初始化
     */
    void init() throws Exception;

    /**
     * 注销
     */
    void unregister(Long id) throws Exception;
}
