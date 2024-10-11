package com.strr.oss.entity;

/**
 * 上传返回体
 *
 * @author Lion Li
 */
public class UploadResult {

    /**
     * 文件路径
     */
    private String url;

    /**
     * 文件名
     */
    private String filename;

    public UploadResult(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
