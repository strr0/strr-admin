package com.strr.data.handle;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * web 处理器
 */
public interface WebHandler {
    @ResponseBody
    Page<?> page(@RequestParam Map<String, Object> param, Pageable pageable);

    @ResponseBody
    Result<Void> save(@RequestBody Map<String, Object> entity);

    @ResponseBody
    Result<Void> update(@RequestBody Map<String, Object> entity);

    @ResponseBody
    Result<Void> remove(@PathVariable(name = "id") String id);

    @ResponseBody
    Result<?> get(@PathVariable(name = "id") String id);
}
