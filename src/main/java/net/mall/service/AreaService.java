/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.Area;

/**
 * Service - 地区
 *
 * @author huanghy
 * @version 6.1
 */
public interface AreaService extends BaseService<Area, Long> {

    /**
     * 查找顶级地区
     *
     * @return 顶级地区
     */
    List<Area> findRoots();

    /**
     * 查找顶级地区
     *
     * @param count 数量
     * @return 顶级地区
     */
    List<Area> findRoots(Integer count);

    /**
     * 查找上级地区
     *
     * @param area      地区
     * @param recursive 是否递归
     * @param count     数量
     * @return 上级地区
     */
    List<Area> findParents(Area area, boolean recursive, Integer count);

    /**
     * 查找下级地区
     *
     * @param area      地区
     * @param recursive 是否递归
     * @param count     数量
     * @return 下级地区
     */
    List<Area> findChildren(Area area, boolean recursive, Integer count);

}