package com.strr.dubbo.config;

import com.strr.factory.YmlPropertySourceFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * dubbo 配置类
 */
@AutoConfiguration
@PropertySource(value = "classpath:common-dubbo.yml", factory = YmlPropertySourceFactory.class)
public class DubboConfiguration {

}
