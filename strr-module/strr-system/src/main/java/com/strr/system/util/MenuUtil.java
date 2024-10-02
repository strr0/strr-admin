package com.strr.system.util;

import com.strr.constant.Constant;
import com.strr.system.model.SysResource;
import com.strr.system.model.vo.SysResourceVo;
import com.strr.system.model.vo.SysRouteMetaVo;
import com.strr.system.model.vo.SysRouteVo;
import com.strr.util.TreeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return TreeUtil.build(resources, resource -> {
            SysResourceVo vo = new SysResourceVo();
            vo.setId(resource.getId());
            vo.setParentId(resource.getParentId());
            vo.setName(resource.getName());
            vo.setPath(resource.getPath());
            vo.setComponent(resource.getComponent());
            vo.setI18nKey(resource.getI18nKey());
            vo.setType(resource.getType());
            vo.setFrame(resource.getFrame());
            vo.setCache(resource.getCache());
            vo.setIcon(resource.getIcon());
            vo.setIconType(resource.getIconType());
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
        Map<Integer, SysRouteVo> routeMap = new HashMap<>();
        Map<Integer, SysRouteVo> parentRouteMap = new HashMap<>();
        List<SysRouteVo> routeTree = new ArrayList<>();
        resources.forEach(resource -> {
            // 获取父级路由
            SysRouteVo parent = routeMap.get(resource.getParentId());
            // 构建 route
            SysRouteVo route = new SysRouteVo();
            if (resource.getPath().contains("/:")) {
                route.setName(resource.getPath().split("/:")[0]);
            } else {
                route.setName(resource.getPath());
            }
            SysRouteMetaVo meta = new SysRouteMetaVo();
            meta.setTitle(resource.getName());
            meta.setI18nKey(resource.getI18nKey());
            meta.setType(resource.getType());
            meta.setIcon(resource.getIcon());
            meta.setOrder(resource.getOrder());
            meta.setPerms(resource.getPerms());
            meta.setKeepAlive(Constant.YES.equals(resource.getCache()));
            boolean hide = Constant.NO.equals(resource.getVisible());
            if (hide) {
                meta.setHideInMenu(true);
                meta.setActiveMenu(parent != null ? parent.getName() : null);
            }
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
            if (hide) {
                // 获取父节点的父节点
                parent = parentRouteMap.get(resource.getParentId());
            }
            // 添加到 routeTree
            if (parent != null) {
                List<SysRouteVo> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(route);
            } else {
                routeTree.add(route);
            }
            routeMap.put(resource.getId(), route);
            parentRouteMap.put(resource.getId(), parent);
        });
        return routeTree;
    }
}
