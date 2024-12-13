package com.strr.data.runner;

import com.strr.data.service.IDmsHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataApplicationRunner implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(DataApplicationRunner.class);
    private final IDmsHandleService dmsHandleService;

    public DataApplicationRunner(IDmsHandleService dmsHandleService) {
        this.dmsHandleService = dmsHandleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dmsHandleService.init();
        logger.info("初始化模块信息成功");
    }
}
