package com.strr.resource.runner;

import com.strr.oss.factory.OssFactory;
import com.strr.oss.properties.OssProperties;
import com.strr.resource.model.SysOssConfig;
import com.strr.resource.service.ISysOssConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceApplicationRunner implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(ResourceApplicationRunner.class);
    private final ISysOssConfigService sysOssConfigService;

    public ResourceApplicationRunner(ISysOssConfigService sysOssConfigService) {
        this.sysOssConfigService = sysOssConfigService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SysOssConfig> configs = sysOssConfigService.listAll();
        for (SysOssConfig config : configs) {
            OssFactory.addClient(config.getConfigKey(), new OssProperties() {{
                setEndpoint(config.getEndpoint());
                setDomain(config.getDomain());
                setPrefix(config.getPrefix());
                setAccessKey(config.getAccessKey());
                setSecretKey(config.getSecretKey());
                setBucketName(config.getBucketName());
                setRegion(config.getRegion());
                setIsHttps(config.getIsHttps());
                setAccessPolicy(config.getAccessPolicy());
            }});
        }
        logger.info("初始化OSS配置成功");
    }
}
