package com.strr.base.model;

/**
 * 分页参数
 */
public class Pageable {
    private Integer page = 1;
    private Integer size = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public <T> Page<T> page() {
        return new Page<>(page, size);
    }
}
