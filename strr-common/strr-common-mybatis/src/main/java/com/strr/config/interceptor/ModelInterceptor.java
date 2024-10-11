package com.strr.config.interceptor;

import com.strr.base.util.LoginUtil;
import com.strr.util.ParameterUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Date;

/**
 * 数据填充
 */
@Intercepts(@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
))
public class ModelInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        // 基础参数
        ParameterUtils.getBaseModel(parameter).ifPresent(model -> {
            Integer userId = LoginUtil.getLoginId();
            SqlCommandType type = ms.getSqlCommandType();
            // 新增
            if (SqlCommandType.INSERT.equals(type)) {
                model.setCreateBy(userId);
                model.setCreateTime(new Date());
            }
            // 修改
            if (SqlCommandType.UPDATE.equals(type)) {
                model.setUpdateBy(userId);
                model.setUpdateTime(new Date());
            }
        });
        return invocation.proceed();
    }
}
