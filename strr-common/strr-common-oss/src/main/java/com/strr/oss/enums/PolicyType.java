package com.strr.oss.enums;

/**
 * minio策略配置
 *
 * @author Lion Li
 */
public enum PolicyType {

    /**
     * 只读
     */
    READ("read-only"),

    /**
     * 只写
     */
    WRITE("write-only"),

    /**
     * 读写
     */
    READ_WRITE("read-write");

    /**
     * 类型
     */
    private final String type;

    PolicyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
