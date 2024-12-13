package com.strr.mybatis.support;

import com.strr.base.mapper.CrudMapper;
import com.strr.base.model.BaseModel;
import com.strr.mybatis.util.ModelUtil;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrudMappedStatement {
    private static final Field[] BASE_FIELDS = BaseModel.class.getDeclaredFields();
    private Class<?> mapperInterface;
    private Class<?> clazz;
    private Field[] fields;
    private boolean build = false;

    private CrudMappedStatement() {}

    public void addCountByParamStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.countByParam", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addListByParamStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.listByParam", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addSaveStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        configuration.setUseGeneratedKeys(true);  // 生成主键key
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.save", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.INSERT
        ).keyProperty("id");  // 主键字段
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addUpdateStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.update", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.UPDATE
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addRemoveStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.remove", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.DELETE
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addGetStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.get", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addPageStatement(Configuration configuration, SqlSource sqlSource, List<ResultMap> resultMaps) {
        MappedStatement.Builder mappedStatementBuilder = new MappedStatement.Builder(
                configuration,
                String.format("%s.page", mapperInterface.getTypeName()),
                sqlSource,
                SqlCommandType.SELECT
        );
        mappedStatementBuilder.resultMaps(resultMaps);
        configuration.addMappedStatement(mappedStatementBuilder.build());
    }

    public void addCrudStatement(Configuration configuration) {
        // 是否生成Crud方法
        if (!build) {
            return;
        }
        String entity = clazz.getSimpleName();
        List<ResultMap> resultMaps = Collections.singletonList(new ResultMap.Builder(configuration, entity, clazz, new ArrayList<>()).build());
        List<ResultMap> simpleResultMaps = Collections.singletonList(new ResultMap.Builder(configuration, entity, int.class, new ArrayList<>()).build());
        String table = ModelUtil.getTable(this.clazz);
        SQL countSql = new SQL();
        countSql.SELECT("count(1)");
        SQL selectSql = new SQL();
        SQL insertSql = new SQL();
        insertSql.INSERT_INTO(table);
        SQL updateSql = new SQL();
        updateSql.UPDATE(table);
        List<SqlNode> updateIfNodes = new ArrayList<>();
        SQL deleteSql = new SQL();
        deleteSql.DELETE_FROM(table);
        // key
        Field key = null;
        // 查询条件
        List<SqlNode> listIfNodes = new ArrayList<>();
        List<SqlNode> pageIfNodes = new ArrayList<>();
        for (Field field : fields) {
            String property = field.getName();
            String column = ModelUtil.getColumn(field);
            selectSql.SELECT(column);
            if (ModelUtil.isKey(field)) {
                key = field;
                continue;
            }
            insertSql.VALUES(column, String.format("#{%s}", property));
            String propertyNotNull = String.format("%s != null", property);
            String propertyNotBlank = String.format("%s && %s != ''", propertyNotNull, property);
            updateIfNodes.add(new IfSqlNode(new TextSqlNode(String.format("%s = #{%s},", ModelUtil.getColumn(field), property)),
                    String.class.equals(field.getType()) ? propertyNotBlank : propertyNotNull));
            // applyWhere
            String condition;
            String pageCondition;
            if (ModelUtil.isFuzzy(field)) {
                condition = String.format(" and %s like \"%%\"#{%s}\"%%\"", column, property);
                pageCondition = String.format(" and %s like \"%%\"#{param.%s}\"%%\"", column, property);
            } else {
                condition = String.format(" and %s = #{%s} ", column, property);
                pageCondition = String.format(" and %s = #{param.%s} ", column, property);
            }
            listIfNodes.add(new IfSqlNode(new TextSqlNode(condition), String.format("%s != null && %s != ''", property, property)));
            pageIfNodes.add(new IfSqlNode(new TextSqlNode(pageCondition), String.format("param.%s != null && param.%s != ''", property, property)));
        }
        // 基础类
        if (BaseModel.class.isAssignableFrom(clazz)) {
            for (Field field : BASE_FIELDS) {
                String property = field.getName();
                String column = ModelUtil.getColumn(field);
                selectSql.SELECT(column);
                insertSql.VALUES(column, String.format("#{%s}", property));
                String propertyNotNull = String.format("%s != null", property);
                String propertyNotBlank = String.format("%s && %s != ''", propertyNotNull, property);
                updateIfNodes.add(new IfSqlNode(new TextSqlNode(String.format("%s = #{%s},", ModelUtil.getColumn(field), property)),
                        String.class.equals(field.getType()) ? propertyNotBlank : propertyNotNull));
            }
        }
        countSql.FROM(table);
        selectSql.FROM(table);
        SqlNode countSqlNode = new StaticTextSqlNode(countSql.toString());
        SqlNode listSqlNode = new StaticTextSqlNode(selectSql.toString());
        SqlNode saveSqlNode = new StaticTextSqlNode(insertSql.toString());
        SqlNode listWhereSqlNode = new WhereSqlNode(configuration, new MixedSqlNode(listIfNodes));
        SqlNode pageWhereSqlNode = new WhereSqlNode(configuration, new MixedSqlNode(pageIfNodes));
        // count
        addCountByParamStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(countSqlNode, listWhereSqlNode))), simpleResultMaps);
        // list
        addListByParamStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(listSqlNode, listWhereSqlNode))), resultMaps);
        // page
        addPageStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(listSqlNode, pageWhereSqlNode))), resultMaps);
        // save
        addSaveStatement(configuration, new RawSqlSource(configuration, saveSqlNode, clazz), simpleResultMaps);
        // 主键不为空
        if (key != null) {
            String keyColumn = ModelUtil.getColumn(key);
            String keyProperty = key.getName();
            // update
            SqlNode updateSqlNode = new StaticTextSqlNode(updateSql.toString());
            SqlNode updateSetSqlNode = new TrimSqlNode(configuration, new MixedSqlNode(updateIfNodes), "SET", null, null, ",");
            SqlNode updateWhereSqlNode = new StaticTextSqlNode(String.format("WHERE %s = #{%s}", keyColumn, keyProperty));
            addUpdateStatement(configuration, new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(updateSqlNode, updateSetSqlNode, updateWhereSqlNode))), simpleResultMaps);
            // remove
            deleteSql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
            SqlNode removeSqlNode = new StaticTextSqlNode(deleteSql.toString());
            addRemoveStatement(configuration, new RawSqlSource(configuration, removeSqlNode, clazz), simpleResultMaps);
            // get
            selectSql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
            SqlNode getSqlNode = new StaticTextSqlNode(selectSql.toString());
            addGetStatement(configuration, new RawSqlSource(configuration, getSqlNode, clazz), resultMaps);
        }
    }

    public static class Builder {
        private final CrudMappedStatement crudMappedStatement = new CrudMappedStatement();

        public Builder(Class<?> mapperInterface) {
            this.crudMappedStatement.mapperInterface = mapperInterface;
        }

        private void preBuild() {
            Class<?> mapperInterface = this.crudMappedStatement.mapperInterface;
            // 是否实现CrudMapper接口
            if (CrudMapper.class.equals(mapperInterface) || !CrudMapper.class.isAssignableFrom(mapperInterface)) {
                return;
            }
            // 获取CrudMapper的泛型参数
            Class<?> clazz = (Class<?>) ((ParameterizedType) mapperInterface.getGenericInterfaces()[0]).getActualTypeArguments()[0];
            this.crudMappedStatement.clazz = clazz;
            this.crudMappedStatement.fields = clazz.getDeclaredFields();
            this.crudMappedStatement.build = true;
        }

        public CrudMappedStatement build() {
            this.preBuild();
            return crudMappedStatement;
        }
    }
}
