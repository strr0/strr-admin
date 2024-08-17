package com.strr.config.mybatis;

import com.strr.base.exception.BuilderException;
import com.strr.base.exception.KeyNotFoundException;
import com.strr.base.model.BaseModel;
import com.strr.util.ModelUtil;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrudSqlSource {
    private static final Field[] baseFields = BaseModel.class.getDeclaredFields();
    private Configuration configuration;
    private Class<?> clazz;
    private Field[] fields;
    private String table;
    private SqlNode applyWhere;
    private SqlNode pageApplyWhere;

    private CrudSqlSource() {}

    public SqlSource countByParamSqlSource() {
        SqlNode sqlNode = countSqlNode();
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(sqlNode, applyWhere)));
    }

    public SqlSource listByParamSqlSource() {
        SqlNode sqlNode = listSqlNode();
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(sqlNode, applyWhere)));
    }

    public SqlSource saveSqlSource() {
        SqlNode sqlNode = saveSqlNode();
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource updateSqlSource() throws BuilderException {
        SqlNode updateSqlNode = updateSqlNode();
        SqlNode updateSetSqlNode = updateSetSqlNode();
        SqlNode updateWhereSqlNode = updateWhereSqlNode();
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(updateSqlNode, updateSetSqlNode, updateWhereSqlNode)));
    }

    public SqlSource removeSqlSource() throws BuilderException {
        SqlNode sqlNode = removeSqlNode();
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource getSqlSource() throws BuilderException {
        SqlNode sqlNode = getSqlNode();
        return new RawSqlSource(configuration, sqlNode, clazz);
    }

    public SqlSource pageSqlSource() {
        SqlNode sqlNode = listSqlNode();
        return new DynamicSqlSource(configuration, new MixedSqlNode(Arrays.asList(sqlNode, pageApplyWhere)));
    }

    private SqlNode countSqlNode() {
        SQL sql = new SQL();
        sql.SELECT("count(1)");
        sql.FROM(table);
        return new StaticTextSqlNode(sql.toString());
    }

    private SqlNode listSqlNode() {
        SQL sql = new SQL();
        for (Field field : fields) {
            sql.SELECT(ModelUtil.getColumn(field));
        }
        sql.FROM(table);
        return new StaticTextSqlNode(sql.toString());
    }

    private SqlNode saveSqlNode() {
        SQL sql = new SQL();
        sql.INSERT_INTO(table);
        for (Field field : fields) {
            if (ModelUtil.isKey(field)) {
                continue;
            }
            sql.VALUES(ModelUtil.getColumn(field), String.format("#{%s}", field.getName()));
        }
        return new StaticTextSqlNode(sql.toString());
    }

    private SqlNode updateSqlNode() {
        return new StaticTextSqlNode(String.format("UPDATE %s", table));
    }

    private SqlNode updateSetSqlNode() {
        List<SqlNode> ifNodes = new ArrayList<>();
        for (Field field : fields) {
            if (ModelUtil.isKey(field)) {
                continue;
            }
            String property = field.getName();
            ifNodes.add(new IfSqlNode(new TextSqlNode(String.format("%s = #{%s},", ModelUtil.getColumn(field), property)),
                    String.format("%s != null && %s != ''", property, property)));
        }
        return new TrimSqlNode(configuration, new MixedSqlNode(ifNodes), "SET", null, null, ",");
    }

    private SqlNode updateWhereSqlNode() throws KeyNotFoundException {
        Field key = ModelUtil.getKey(fields);
        String keyColumn = ModelUtil.getColumn(key);
        String keyProperty = key.getName();
        return new StaticTextSqlNode(String.format("WHERE %s = #{%s}", keyColumn, keyProperty));
    }

    private SqlNode removeSqlNode() throws KeyNotFoundException {
        Field key = ModelUtil.getKey(fields);
        String keyColumn = ModelUtil.getColumn(key);
        String keyProperty = key.getName();
        SQL sql = new SQL();
        sql.DELETE_FROM(table);
        sql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
        return new StaticTextSqlNode(sql.toString());
    }

    private SqlNode getSqlNode() throws KeyNotFoundException {
        Field key = ModelUtil.getKey(fields);
        String keyColumn = ModelUtil.getColumn(key);
        String keyProperty = key.getName();
        SQL sql = new SQL();
        for (Field field : fields) {
            sql.SELECT(ModelUtil.getColumn(field));
        }
        sql.FROM(table);
        sql.WHERE(String.format("%s = #{%s}", keyColumn, keyProperty));
        return new StaticTextSqlNode(sql.toString());
    }

    public static class Builder {
        private final CrudSqlSource crudSqlSource = new CrudSqlSource();

        public Builder(Configuration configuration, Class<?> clazz) {
            this.crudSqlSource.configuration = configuration;
            this.crudSqlSource.clazz = clazz;
        }

        private void preBuild() {
            Class<?> clazz =  this.crudSqlSource.clazz;
            Field[] fields = clazz.getDeclaredFields();
            // 表名
            this.crudSqlSource.table = ModelUtil.getTable(this.crudSqlSource.clazz);
            // 查询条件
            List<SqlNode> ifNodes = new ArrayList<>();
            List<SqlNode> pageIfNodes = new ArrayList<>();
            for (Field field : fields) {
                String property = field.getName();
                String column = ModelUtil.getColumn(field);
                String condition = ModelUtil.isFuzzy(field) ? String.format(" and %s like \"%%\"#{%s}\"%%\"", column, property) :
                        String.format(" and %s = #{%s} ", column, property);
                ifNodes.add(new IfSqlNode(new TextSqlNode(condition), String.format("%s != null && %s != ''", property, property)));
                // 分页
                String pageCondition = ModelUtil.isFuzzy(field) ? String.format(" and %s like \"%%\"#{param.%s}\"%%\"", column, property) :
                        String.format(" and %s = #{param.%s} ", column, property);
                pageIfNodes.add(new IfSqlNode(new TextSqlNode(pageCondition), String.format("param.%s != null && param.%s != ''", property, property)));
            }

            // 基础类
            if (BaseModel.class.isAssignableFrom(clazz)) {
                Field[] allFields = new Field[fields.length + baseFields.length];
                System.arraycopy(fields, 0, allFields, 0, fields.length);
                System.arraycopy(baseFields, 0, allFields, fields.length, baseFields.length);
                fields = allFields;
            }
            this.crudSqlSource.fields = fields;

            this.crudSqlSource.applyWhere = new WhereSqlNode(this.crudSqlSource.configuration, new MixedSqlNode(ifNodes));
            this.crudSqlSource.pageApplyWhere = new WhereSqlNode(this.crudSqlSource.configuration, new MixedSqlNode(pageIfNodes));
        }

        public CrudSqlSource build() {
            this.preBuild();
            return crudSqlSource;
        }
    }
}
