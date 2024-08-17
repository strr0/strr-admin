package com.strr.util;

import com.strr.tree.TreeConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 树工具类
 */
public class TreeUtil {
    public static <S, T, K> List<T> build(List<S> list, TreeConfig<S, T, K> config, Function<S, T> transform) {
        Map<K, T> itemMap = new HashMap<>();
        List<T> tree = new ArrayList<>();
        list.forEach(source -> {
            T target = transform.apply(source);
            T parent = itemMap.get(config.getParentIdApply(source));
            if (parent != null) {
                List<T> children = config.getChildrenApply(parent);
                if (children == null) {
                    children = new ArrayList<>();
                    config.setChildrenAccept(parent, children);
                }
                children.add(target);
            } else {
                tree.add(target);
            }
            itemMap.put(config.getIdApply(source), target);
        });
        return tree;
    }
}
