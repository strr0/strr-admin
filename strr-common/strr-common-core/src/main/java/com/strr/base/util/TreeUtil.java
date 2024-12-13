package com.strr.base.util;

import com.strr.base.model.Treeable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 树工具类
 */
public class TreeUtil {
    public static <T extends Treeable<T, K>, K> List<T> build(List<T> list, K pid) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<T> tree = new ArrayList<>();
        list.forEach(item -> {
            if (pid.equals(item.getParentId())) {
                item.setChildren(build(list, item.getId()));
                tree.add(item);
            }
        });
        return tree;
    }

    public static <T extends Treeable<T, K>, K> List<T> build(List<T> list) {
        Map<K, T> itemMap = new HashMap<>();
        List<T> tree = new ArrayList<>();
        list.forEach(item -> {
            T parent = itemMap.get(item.getParentId());
            if (parent != null) {
                List<T> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(item);
            } else {
                tree.add(item);
            }
            itemMap.put(item.getId(), item);
        });
        return tree;
    }

    public static <S, T extends Treeable<T, K>, K> List<T> build(List<S> list, Function<S, T> transform) {
        Map<K, T> itemMap = new HashMap<>();
        List<T> tree = new ArrayList<>();
        list.forEach(source -> {
            T target = transform.apply(source);
            T parent = itemMap.get(target.getParentId());
            if (parent != null) {
                List<T> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(target);
            } else {
                tree.add(target);
            }
            itemMap.put(target.getId(), target);
        });
        return tree;
    }
}
