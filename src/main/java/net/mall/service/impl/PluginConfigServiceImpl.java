/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.dao.PluginConfigDao;
import net.mall.entity.PluginConfig;
import net.mall.service.PluginConfigService;

/**
 * Service - 插件配置
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class PluginConfigServiceImpl extends BaseServiceImpl<PluginConfig, Long> implements PluginConfigService {

    @Inject
    private PluginConfigDao pluginConfigDao;
    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public boolean pluginIdExists(String pluginId) {
        return pluginConfigDao.exists("pluginId", pluginId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("pluginConfig")
    public PluginConfig findByPluginId(String pluginId) {
        return pluginConfigDao.find("pluginId", pluginId);
    }


    /***
     * getNoCachePluginConfig方法慨述:直接取数据库数据
     * @param pluginId
     * @return PluginConfig
     * @创建人 huanghy
     * @创建时间 2020年1月6日 下午4:13:50
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @Override
    @Transactional(readOnly = true)
    public PluginConfig getNoCachePluginConfig(String pluginId) {
        PluginConfig pluginConfig = pluginConfigDao.find("pluginId", pluginId);
        if (entityManager.contains(pluginConfig)) {
            entityManager.clear();
        }
        return pluginConfig;
    }

    @Override
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public void deleteByPluginId(String pluginId) {
        PluginConfig pluginConfig = findByPluginId(pluginId);
        pluginConfigDao.remove(pluginConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public PluginConfig save(PluginConfig pluginConfig) {
        return super.save(pluginConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public PluginConfig update(PluginConfig pluginConfig) {
        return super.update(pluginConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public PluginConfig update(PluginConfig pluginConfig, String... ignoreProperties) {
        return super.update(pluginConfig, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "pluginConfig", allEntries = true)
    public void delete(PluginConfig pluginConfig) {
        super.delete(pluginConfig);
    }

}