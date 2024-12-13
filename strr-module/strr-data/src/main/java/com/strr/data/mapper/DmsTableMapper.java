package com.strr.data.mapper;

import com.strr.base.mapper.CrudMapper;
import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.data.model.DmsTable;
import com.strr.data.model.bo.DmsTableBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务表信息
 */
public interface DmsTableMapper extends CrudMapper<DmsTable, Long> {
    /**
     * 获取所有业务表名
     */
    List<String> listName();

    /**
     * 查询数据库表信息
     */
    Page<DmsTable> pageDbTable(@Param("param") DmsTableBo param, @Param("page") Pageable pageable);

    /**
     * 根据表名获取数据库表信息
     */
    List<DmsTable> listDbTableByNames(String[] names);
}
