/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.entity.PluginConfig;

/**
 * Service - 插件配置
 *
 * @author huanghy
 * @version 6.1
 */
public interface PluginConfigService extends BaseService<PluginConfig, Long> {

    /**
     * 判断插件ID是否存在
     *
     * @param pluginId 插件ID
     * @return 插件ID是否存在
     */
    boolean pluginIdExists(String pluginId);

    /**
     * 根据插件ID查找插件配置
     *
     * @param pluginId 插件ID
     * @return 插件配置，若不存在则返回null
     */
    PluginConfig findByPluginId(String pluginId);

    /***
     * getNoCachePluginConfig方法慨述:直接取数据库数据
     * @param pluginId
     * @return PluginConfig
     * @创建人 huanghy
     * @创建时间 2020年1月6日 下午4:13:50
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    PluginConfig getNoCachePluginConfig(String pluginId);

    /**
     * 根据插件ID删除插件配置
     *
     * @param pluginId 插件ID
     */
    void deleteByPluginId(String pluginId);

}