package com.strr.resource.service;

import com.strr.base.service.ICrudService;
import com.strr.resource.model.SysOss;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ISysOssService extends ICrudService<SysOss, Integer> {
    /**
     * 文件上传
     */
    SysOss upload(MultipartFile file);

    /**
     * 文件下载
     */
    void download(Integer id, HttpServletResponse response) throws IOException;
}
