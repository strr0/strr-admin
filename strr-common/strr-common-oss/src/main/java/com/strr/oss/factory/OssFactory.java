package com.strr.oss.factory;

import com.strr.base.util.StringUtil;
import com.strr.oss.constant.OssConstant;
import com.strr.oss.core.OssClient;
import com.strr.oss.exception.OssException;
import com.strr.oss.properties.OssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件上传Factory
 *
 * @author Lion Li
 */
public class OssFactory {
    private static final Logger logger = LoggerFactory.getLogger(OssFactory.class);
    private static final Map<String, OssClient> CLIENT_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取默认实例
     */
    public static OssClient instance() {
        // 获取redis 默认类型
        String configKey = OssConstant.DEFAULT_CONFIG_KEY;
        if (StringUtil.isEmpty(configKey)) {
            throw new OssException("文件存储服务类型无法找到!");
        }
        return instance(configKey);
    }

    /**
     * 根据类型获取实例
     */
    public static OssClient instance(String configKey) {
        OssClient client = CLIENT_CACHE.get(configKey);
        if (client == null) {
            throw new OssException("系统异常, '" + configKey + "'配置信息不存在!");
        }
        return client;
    }

    /**
     * 添加实例
     */
    public static void addClient(String configKey, OssProperties properties) {
        CLIENT_CACHE.put(configKey, new OssClient(configKey, properties));
    }
}
