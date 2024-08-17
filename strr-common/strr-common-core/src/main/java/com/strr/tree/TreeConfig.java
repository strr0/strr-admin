package com.strr.tree;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 树配置
 */
public class TreeConfig<S, T, K> {
    /**
     * 获取id
     */
    private Function<S, K> getId;

    /**
     * 获取父id
     */
    private Function<S, K> getParentId;

    /**
     * 获取子节点
     */
    private Function<T, List<T>> getChildren;

    /**
     * 设置子节点
     */
    private BiConsumer<T, List<T>> setChildren;

    private TreeConfig() {}

    public K getIdApply(S source) {
        return getId.apply(source);
    }

    public K getParentIdApply(S source) {
        return getParentId.apply(source);
    }

    public List<T> getChildrenApply(T target) {
        return getChildren.apply(target);
    }

    public void setChildrenAccept(T target, List<T> children) {
        setChildren.accept(target, children);
    }

    public static class Builder<S, T, K> {
        private final TreeConfig<S, T, K> config = new TreeConfig<>();

        public Builder<S, T, K> getId(Function<S, K> getId) {
            config.getId = getId;
            return this;
        }

        public Builder<S, T, K> getParentId(Function<S, K> getParentId) {
            config.getParentId = getParentId;
            return this;
        }

        public Builder<S, T, K> getChildren(Function<T, List<T>> getChildren) {
            config.getChildren = getChildren;
            return this;
        }

        public Builder<S, T, K> setChildren(BiConsumer<T, List<T>> setChildren) {
            config.setChildren = setChildren;
            return this;
        }

        public TreeConfig<S, T, K> build() {
            if (config.getId == null) {
                throw new IllegalStateException("getId must not be null");
            }
            if (config.getParentId == null) {
                throw new IllegalStateException("getParentId must not be null");
            }
            if (config.getChildren == null) {
                throw new IllegalStateException("getChildren must not be null");
            }
            if (config.setChildren == null) {
                throw new IllegalStateException("setChildren must not be null");
            }
            return config;
        }
    }
}
