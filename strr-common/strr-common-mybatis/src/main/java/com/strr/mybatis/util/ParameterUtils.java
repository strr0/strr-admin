package com.strr.mybatis.util;

import com.strr.base.model.BaseModel;
import com.strr.base.model.Pageable;

import java.util.Map;
import java.util.Optional;

/**
 * 参数工具类
 */
public class ParameterUtils {
    /**
     * 获取基础类
     */
    public static Optional<BaseModel> getBaseModel(Object parameter) {
        return Optional.ofNullable(parameter).map(param -> {
            if (param instanceof Map<?, ?> map) {
                for (Object value : map.values()) {
                    if (value instanceof BaseModel model) {
                        return model;
                    }
                }
            } else if (param instanceof BaseModel model) {
                return model;
            }
            return null;
        });
    }

    /**
     * 获取分页参数
     */
    public static Optional<Pageable> getPageable(Object parameter) {
        return Optional.ofNullable(parameter).map(param -> {
            if (param instanceof Map<?, ?> map) {
                for (Object value : map.values()) {
                    if (value instanceof Pageable pageable) {
                        return pageable;
                    }
                }
            } else if (param instanceof Pageable pageable) {
                return pageable;
            }
            return null;
        });
    }
}
