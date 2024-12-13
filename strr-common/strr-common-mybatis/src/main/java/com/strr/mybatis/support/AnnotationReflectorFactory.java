package com.strr.mybatis.support;

import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.util.MapUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 注解反射器工厂
 */
public class AnnotationReflectorFactory implements ReflectorFactory {
    private boolean classCacheEnabled = true;
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

    @Override
    public boolean isClassCacheEnabled() {
        return this.classCacheEnabled;
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    @Override
    public Reflector findForClass(Class<?> type) {
        return this.classCacheEnabled ? MapUtil.computeIfAbsent(this.reflectorMap, type, AnnotationReflector::new) : new AnnotationReflector(type);
    }
}
