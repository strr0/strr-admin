package com.strr.system.util;

import com.strr.constant.Constant;
import com.strr.system.model.SysResource;
import com.strr.system.model.vo.SysResourceVo;
import com.strr.system.model.vo.SysRouteMetaVo;
import com.strr.system.model.vo.SysRouteVo;
import com.strr.tree.TreeConfig;
import com.strr.util.TreeUtil;

import java.util.List;

/**
 * 菜单工具
 */
public class MenuUtil {
    private static final int ROOT_ID = 0;

    private static final String DIRECTORY_TYPE = "D";  // 目录
    private static final String MENU_TYPE = "M";  // 菜单
    private static final String BTN_TYPE = "B";  // 按钮

    private static final String BASE_LAYOUT = "base";  // 基础布局
    private static final String BLANK_LAYOUT = "blank";  // 空白布局

    /**
     * 菜单树
     */
    public static List<SysResourceVo> buildMenuTree(List<SysResource> resources) {
        TreeConfig<SysResource, SysResourceVo, Integer> config = new TreeConfig.Builder<SysResource, SysResourceVo, Integer>()
                .getId(SysResource::getId)
                .getParentId(SysResource::getParentId)
                .getChildren(SysResourceVo::getChildren)
                .setChildren(SysResourceVo::setChildren)
                .build();
        return TreeUtil.build(resources, config, resource -> {
            SysResourceVo vo = new SysResourceVo();
            vo.setId(resource.getId());
            vo.setParentId(resource.getParentId());
            vo.setName(resource.getName());
            vo.setPath(resource.getPath());
            vo.setComponent(resource.getComponent());
            vo.setType(resource.getType());
            vo.setFrame(resource.getFrame());
            vo.setCache(resource.getCache());
            vo.setIcon(resource.getIcon());
            vo.setOrder(resource.getOrder());
            vo.setVisible(resource.getVisible());
            vo.setPerms(resource.getPerms());
            vo.setRemark(resource.getRemark());
            vo.setStatus(resource.getStatus());
            return vo;
        });
    }

    /**
     * 路由树
     */
    public static List<SysRouteVo> buildRouteTree(List<SysResource> resources) {
        TreeConfig<SysResource, SysRouteVo, Integer> config = new TreeConfig.Builder<SysResource, SysRouteVo, Integer>()
                .getId(SysResource::getId)
                .getParentId(SysResource::getParentId)
                .getChildren(SysRouteVo::getChildren)
                .setChildren(SysRouteVo::setChildren)
                .build();
        return TreeUtil.build(resources, config, resource -> {
            SysRouteVo route = new SysRouteVo();
            route.setName(resource.getPath());
            SysRouteMetaVo meta = new SysRouteMetaVo();
            meta.setTitle(resource.getName());
            meta.setType(resource.getType());
            meta.setIcon(resource.getIcon());
            meta.setOrder(resource.getOrder());
            meta.setKeepAlive(Constant.YES.equals(resource.getCache()));
            meta.setHideInMenu(Constant.NO.equals(resource.getVisible()));
            // 是否外链
            if (Constant.YES.equals(resource.getFrame())) {
                meta.setHref(resource.getPath());
            } else {
                route.setPath("/" + resource.getPath());
            }
            // 路由组件
            boolean topLevel = resource.getParentId() == null || resource.getParentId() == ROOT_ID;
            if (DIRECTORY_TYPE.equals(resource.getType())) {
                if (topLevel) {
                    route.setComponent(BASE_LAYOUT);
                }
            } else if (MENU_TYPE.equals(resource.getType())) {
                if (topLevel) {
                    meta.setSingle(true);
                }
                route.setComponent(resource.getComponent());
            }
            route.setMeta(meta);
            return route;
        });
    }
}
