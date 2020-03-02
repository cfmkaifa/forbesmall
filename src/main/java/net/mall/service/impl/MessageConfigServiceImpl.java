/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.dao.MessageConfigDao;
import net.mall.entity.MessageConfig;
import net.mall.service.MessageConfigService;

/**
 * Service - 消息配置
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class MessageConfigServiceImpl extends BaseServiceImpl<MessageConfig, Long> implements MessageConfigService {

    @Inject
    private MessageConfigDao messageConfigDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable("messageConfig")
    public MessageConfig find(MessageConfig.Type type) {
        return messageConfigDao.find("type", type);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public MessageConfig save(MessageConfig messageConfig) {
        return super.save(messageConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public MessageConfig update(MessageConfig messageConfig) {
        return super.update(messageConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public MessageConfig update(MessageConfig messageConfig, String... ignoreProperties) {
        return super.update(messageConfig, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "messageConfig", allEntries = true)
    public void delete(MessageConfig messageConfig) {
        super.delete(messageConfig);
    }

}