package com.strr.config;

import com.strr.config.mybatis.CrudMapperFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = { "com.strr.admin.mapper" }, factoryBean = CrudMapperFactoryBean.class)
public class MybatisConfig extends DefaultMybatisConfig{
    public MybatisConfig(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}
