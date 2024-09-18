package com.strr.data.handle;

import com.strr.constant.Constant;
import com.strr.data.model.DmsColumn;
import com.strr.data.model.DmsTable;
import com.strr.data.model.vo.DmsModuleVo;
import com.strr.data.util.KeywordUtil;
import com.strr.util.ModelUtil;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import java.util.*;

public class DaoHandler {
    private String moduleCode;
    private DmsTable table;
    private List<DmsColumn> columns;

    private DaoHandler() {}

    private void buildPageMappedStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.page", moduleCode),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    private void buildSaveMappedStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        configuration.setUseGeneratedKeys(true);  // 生成主键key
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.save", moduleCode),
                sqlSource,
                SqlCommandType.INSERT
        ).keyProperty("id");  // 主键字段
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    private void buildUpdateMappedStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.update", moduleCode),
                sqlSource,
                SqlCommandType.UPDATE
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    private void buildRemoveMappedStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.remove", moduleCode),
                sqlSource,
                SqlCommandType.DELETE
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    private void buildGetMappedStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.get", moduleCode),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    private void buildMappedStatement(Configuration configuration) {
        List<ResultMap> resultMaps = Collections.singletonList(new ResultMap.Builder(configuration, table.getName(), Map.class, new ArrayList<>()).build());
        List<ResultMap> simpleResultMaps = Collections.singletonList(new ResultMap.Builder(configuration, table.getName(), int.class, new ArrayList<>()).build());
        // 构建 sql 语句
        SQL selectSql = new SQL();
        List<SqlNode> pageIfNodes = new ArrayList<>();
        SQL insertSql = new SQL();
        insertSql.INSERT_INTO(table.getName());
        SQL updateSql = new SQL();
        updateSql.UPDATE(table.getName());
        List<SqlNode> updateIfNodes = new ArrayList<>();
        SQL deleteSql = new SQL();
        deleteSql.DELETE_FROM(table.getName());
        DmsColumn keyColumn = null;
        for (DmsColumn column : columns) {
            String columnName = column.getName();
            String realName = KeywordUtil.getName(columnName);
            String camelCaseName = ModelUtil.toCamelCase(columnName);
            selectSql.SELECT(realName);
            if (Constant.YES.equals(column.getPk())) {
                keyColumn = column;
                continue;
            }
            String pageCondition = String.format(" and %s = #{param.%s} ", realName, camelCaseName);
            pageIfNodes.add(new IfSqlNode(new TextSqlNode(pageCondition), String.format("param.%s != null && param.%s != ''", camelCaseName, camelCaseName)));
            insertSql.VALUES(realName, String.format("#{%s}", camelCaseName));
            updateIfNodes.add(new IfSqlNode(new TextSqlNode(String.format("%s = #{%s},", realName, camelCaseName)),
                    String.format("%s != null && %s != ''", camelCaseName, camelCaseName)));
        }
        selectSql.FROM(table.getName());
        SqlNode pageSqlNode = new StaticTextSqlNode(selectSql.toString());
        SqlNode pageWhereSqlNode = new WhereSqlNode(configuration, new MixedSqlNode(pageIfNodes));
        SqlNode saveSqlNode = new StaticTextSqlNode(insertSql.toString());
        // 分页
        buildPageMappedStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(pageSqlNode, pageWhereSqlNode))), resultMaps);
        // 保存
        buildSaveMappedStatement(configuration, new RawSqlSource(configuration, saveSqlNode, Map.class), simpleResultMaps);
        // 主键不为空
        if (keyColumn != null) {
            String columnName = keyColumn.getName();
            String realName = KeywordUtil.getName(columnName);
            String camelCaseName = ModelUtil.toCamelCase(columnName);
            // 修改
            SqlNode updateSqlNode = new StaticTextSqlNode(updateSql.toString());
            SqlNode updateWhereSqlNode = new StaticTextSqlNode(String.format("WHERE %s = #{%s}", realName, camelCaseName));
            SqlNode updateSetSqlNode = new TrimSqlNode(configuration, new MixedSqlNode(updateIfNodes), "SET", null, null, ",");
            buildUpdateMappedStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(updateSqlNode, updateSetSqlNode, updateWhereSqlNode))), simpleResultMaps);
            // 删除
            deleteSql.WHERE(String.format("%s = #{%s}", realName, camelCaseName));
            SqlNode removeSqlNode = new StaticTextSqlNode(deleteSql.toString());
            buildRemoveMappedStatement(configuration, new RawSqlSource(configuration, removeSqlNode, Map.class), simpleResultMaps);
            // 详情
            selectSql.WHERE(String.format("%s = #{%s}", realName, camelCaseName));
            SqlNode getSqlNode = new StaticTextSqlNode(selectSql.toString());
            buildGetMappedStatement(configuration, new RawSqlSource(configuration, getSqlNode, Map.class), resultMaps);
        }
    }

    public void handle(Configuration configuration) {
        buildMappedStatement(configuration);
    }

    public static class Builder {
        private final DaoHandler daoHandler = new DaoHandler();

        public Builder(DmsModuleVo module) {
            this.daoHandler.moduleCode = module.getCode();
            this.daoHandler.table = module.getTable();
            this.daoHandler.columns = module.getColumns();
        }

        public DaoHandler build() {
            return daoHandler;
        }
    }
}
