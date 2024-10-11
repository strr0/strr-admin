package com.strr.resource.service.impl;

import com.strr.base.service.impl.CrudServiceImpl;
import com.strr.oss.core.OssClient;
import com.strr.oss.entity.UploadResult;
import com.strr.oss.enums.AccessPolicyType;
import com.strr.oss.factory.OssFactory;
import com.strr.resource.mapper.SysOssMapper;
import com.strr.resource.model.SysOss;
import com.strr.resource.service.ISysOssService;
import com.strr.util.FileUtil;
import com.strr.util.IoUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SysOssServiceImpl extends CrudServiceImpl<SysOss, Integer> implements ISysOssService {
    private final SysOssMapper sysOssMapper;

    public SysOssServiceImpl(SysOssMapper sysOssMapper) {
        this.sysOssMapper = sysOssMapper;
    }

    @Override
    protected SysOssMapper getMapper() {
        return sysOssMapper;
    }

    /**
     * 文件上传
     */
    @Override
    public SysOss upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = originalfileName.substring(originalfileName.lastIndexOf("."));
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult);
    }

    private SysOss buildResultEntity(String originalfileName, String suffix, String configKey, UploadResult uploadResult) {
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalfileName);
        oss.setService(configKey);
        sysOssMapper.save(oss);
        matchingUrl(oss);
        return oss;
    }

    /**
     * 文件下载
     */
    @Override
    public void download(Integer id, HttpServletResponse response) throws IOException {
        SysOss sysOss = sysOssMapper.get(id);
        if (sysOss == null) {
            throw new RuntimeException("文件数据不存在!");
        }
        FileUtil.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        OssClient storage = OssFactory.instance(sysOss.getService());
        try(InputStream inputStream = storage.getObjectContent(sysOss.getUrl())) {
            int available = inputStream.available();
            IoUtil.copy(inputStream, response.getOutputStream());
            response.setContentLength(available);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 文件删除
     */
    @Override
    public int remove(Integer id) {
        SysOss sysOss = sysOssMapper.get(id);
        OssClient storage = OssFactory.instance(sysOss.getService());
        storage.delete(sysOss.getUrl());
        return sysOssMapper.remove(id);
    }

    /**
     * 匹配Url
     */
    private void matchingUrl(SysOss oss) {
        OssClient storage = OssFactory.instance(oss.getService());
        // 仅修改桶类型为 private 的URL，临时URL时长为120s
        if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
            oss.setUrl(storage.getPrivateUrl(oss.getFileName(), 120));
        }
    }
}
