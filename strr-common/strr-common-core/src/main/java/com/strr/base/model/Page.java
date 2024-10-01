package com.strr.base.model;

import java.util.List;

/**
 * 分页对象
 */
public class Page<T> extends Pageable {
    private Integer total;
    private List<T> content;

    public Page(Integer page, Integer size) {
        super.setPage(page);
        super.setSize(size);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public static <T> Page<T> of(Pageable pageable) {
        return new Page<>(pageable.getPage(), pageable.getSize());
    }
}
