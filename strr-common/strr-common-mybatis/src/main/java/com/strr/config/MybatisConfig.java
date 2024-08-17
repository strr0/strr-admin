package com.strr.config;

import com.strr.config.interceptor.CustomInterceptor;
import com.strr.config.mybatis.AnnotationReflectorFactory;
import com.strr.config.mybatis.CrudMapperFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@MapperScan(basePackages = { "${mybatis.crud-mapper-package}" }, factoryBean = CrudMapperFactoryBean.class)
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return config -> {
            config.setReflectorFactory(new AnnotationReflectorFactory());
            // 开启下划线转驼峰
            config.setMapUnderscoreToCamelCase(true);
            config.addInterceptor(new CustomInterceptor());
        };
    }
}
