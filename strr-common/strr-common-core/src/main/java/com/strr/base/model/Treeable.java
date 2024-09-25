package com.strr.base.model;

import java.util.List;

/**
 * 可树化
 */
public interface Treeable<T extends Treeable<T, K>, K> {
    /**
     * 获取 id
     */
    K getId();

    /**
     * 获取父级 id
     */
    K getParentId();

    /**
     * 获取子节点
     */
    List<T> getChildren();

    /**
     * 设置子节点
     */
    void setChildren(List<T> children);
}
