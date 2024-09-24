package com.strr.config.interceptor;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.util.LoginUtil;
import com.strr.config.interceptor.page.IDialect;
import com.strr.config.interceptor.page.MySqlDialect;
import com.strr.util.ParameterUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义插件
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class CustomInterceptor implements Interceptor {
    /**
     * countSql 后缀
     */
    public static final String COUNT_SUFFIX = "-inline-count";

    /**
     * count 缓存
     */
    private static final Map<String, MappedStatement> countMsCache = new ConcurrentHashMap<>();

    /**
     * sql 方言
     */
    private static final IDialect dialect = new MySqlDialect();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
        Executor executor = (Executor)invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        SqlCommandType type = ms.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(type) || SqlCommandType.UPDATE.equals(type)) {
            // 基础参数
            ParameterUtils.getBaseModel(parameter).ifPresent(model -> {
                Integer userId = LoginUtil.getLoginId();
                if (model.getCreateBy() == null) {
                    model.setCreateBy(userId);
                }
                if (model.getCreateTime() == null) {
                    model.setCreateTime(new Date());
                }
                if (model.getUpdateBy() == null) {
                    model.setUpdateBy(userId);
                }
                if (model.getUpdateTime() == null) {
                    model.setUpdateTime(new Date());
                }
            });
            return invocation.proceed();
        }

        // 分页参数
        Optional<Pageable> optional = ParameterUtils.getPageable(parameter);
        if (optional.isEmpty()) {
            return invocation.proceed();
        }
        Pageable pageable = optional.get();

        Page<Object> page = pageable.page();
        MappedStatement countMs = buildCountMappedStatement(ms);
        Integer count = executeCount(executor, countMs, parameter, boundSql, rowBounds, resultHandler);
        page.setTotal(count);

        if (count > 0) {
            List<Object> list = pageQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey, pageable);
            page.setContent(list);
        } else {
            page.setContent(Collections.emptyList());
        }

        return Collections.singletonList(page);
    }

    /**
     * 构建 count
     */
    private MappedStatement buildCountMappedStatement(MappedStatement ms) {
        String countId = ms.getId() + COUNT_SUFFIX;
        Configuration configuration = ms.getConfiguration();
        return countMsCache.computeIfAbsent(countId, key -> {
            MappedStatement.Builder builder = new MappedStatement.Builder(configuration, key, ms.getSqlSource(), ms.getSqlCommandType());
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            builder.resultMaps(Collections.singletonList(new ResultMap.Builder(configuration, key, Integer.class, Collections.emptyList()).build()));
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            return builder.build();
        });
    }

    /**
     * 执行 count
     */
    private Integer executeCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, RowBounds rowBounds, ResultHandler<?> resultHandler) throws SQLException {
        CacheKey countKey = executor.createCacheKey(countMs, parameter, rowBounds, boundSql);
        String countSql = String.format("select count(1) from (%s) t", boundSql.getSql());
        BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        List<Number> countResultList = executor.query(countMs, parameter, rowBounds, resultHandler, countKey, countBoundSql);
        return countResultList != null && !countResultList.isEmpty() ? countResultList.get(0).intValue() : 0;
    }

    /**
     * 执行分页
     */
    private <E> List<E> pageQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler<?> resultHandler, BoundSql boundSql, CacheKey cacheKey, Pageable pageable) throws SQLException {
        String pageSql = dialect.getPageSql(boundSql.getSql(), pageable.getPage(), pageable.getSize());
        BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, pageBoundSql);
    }
}
