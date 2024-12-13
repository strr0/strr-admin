package com.strr.resource.controller;

import com.strr.base.controller.CrudController;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.resource.model.SysOss;
import com.strr.resource.service.ISysOssService;
import com.strr.security.annotation.CheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${module.resource:}/oss")
public class SysOssController extends CrudController<SysOss, Integer> {
    private final ISysOssService sysOssService;

    public SysOssController(ISysOssService sysOssService) {
        this.sysOssService = sysOssService;
    }

    @Override
    protected ISysOssService getService() {
        return sysOssService;
    }

    /**
     * 查询对象存储列表
     */
    @Override
    @CheckPermission("system:oss:list")
    @GetMapping("/page")
    public Result<Page<SysOss>> page(SysOss param, Pageable pageable) {
        return super.page(param, pageable);
    }

    /**
     * 文件上传
     */
    @CheckPermission("system:oss:upload")
    @PostMapping("/upload")
    public Result<SysOss> upload(@RequestPart("file") MultipartFile file) {
        SysOss sysOss = sysOssService.upload(file);
        return Result.ok(sysOss);
    }

    /**
     * 文件下载
     */
    @CheckPermission("system:oss:download")
    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        sysOssService.download(id, response);
    }

    /**
     * 文件删除
     */
    @Override
    @CheckPermission("system:oss:remove")
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        return super.remove(id);
    }

    /**
     * 获取对象存储详情
     */
    @Override
    @CheckPermission("system:oss:query")
    @GetMapping("/{id}")
    public Result<SysOss> get(@PathVariable Integer id) {
        return super.get(id);
    }
}
