package com.strr.data.handle.factory;

import com.strr.base.model.Page;
import com.strr.base.model.Pageable;
import com.strr.base.model.Result;
import com.strr.data.handle.WebHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * web 处理器工厂
 */
public class WebHandlerFactory {
    public static WebHandler buildWebHandler(SqlSessionFactory sqlSessionFactory, String code) {
        Result<Void> OK = Result.ok();
        Result<Void> ERROR = Result.error();
        return new WebHandler() {
            @Override
            public Result<Page<?>> page(Map<String, Object> param, Pageable pageable) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                Map<String, Object> map = new HashMap<>();
                map.put("param", param);
                map.put("page", pageable);
                Page<?> page = sqlSession.selectOne(String.format("%s.page", code), map);
                sqlSession.close();
                return Result.ok(page);
            }

            @Override
            public Result<Void> save(Map<String, Object> entity) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                int row = sqlSession.insert(String.format("%s.save", code), entity);
                sqlSession.close();
                if (row > 0) {
                    return OK;
                }
                return ERROR;
            }

            @Override
            public Result<Void> update(Map<String, Object> entity) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                int row = sqlSession.update(String.format("%s.update", code), entity);
                sqlSession.close();
                if (row > 0) {
                    return OK;
                }
                return ERROR;
            }

            @Override
            public Result<Void> remove(String id) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                int row = sqlSession.delete(String.format("%s.remove", code), id);
                sqlSession.close();
                if (row > 0) {
                    return OK;
                }
                return ERROR;
            }

            @Override
            public Result<?> get(String id) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                Map<String, Object> map = sqlSession.selectOne(String.format("%s.get", code), id);
                return Result.ok(map);
            }
        };
    }

    public static void registerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping, WebHandler webHandler, String path) throws NoSuchMethodException {
        // page
        RequestMappingInfo page = RequestMappingInfo.paths(String.format("/%s/page", path)).methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(page, webHandler, WebHandler.class.getMethod("page", Map.class, Pageable.class));
        // save
        RequestMappingInfo save = RequestMappingInfo.paths(String.format("/%s", path)).methods(RequestMethod.POST).build();
        requestMappingHandlerMapping.registerMapping(save, webHandler, WebHandler.class.getMethod("save", Map.class));
        // update
        RequestMappingInfo update = RequestMappingInfo.paths(String.format("/%s", path)).methods(RequestMethod.PUT).build();
        requestMappingHandlerMapping.registerMapping(update, webHandler, WebHandler.class.getMethod("update", Map.class));
        // remove
        RequestMappingInfo remove = RequestMappingInfo.paths(String.format("/%s/{id}", path)).methods(RequestMethod.DELETE).build();
        requestMappingHandlerMapping.registerMapping(remove, webHandler, WebHandler.class.getMethod("remove", String.class));
        // get
        RequestMappingInfo get = RequestMappingInfo.paths(String.format("/%s/{id}", path)).methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(get, webHandler, WebHandler.class.getMethod("get", String.class));
    }
}
