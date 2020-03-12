/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.SpecificationItem;

/**
 * Service - 规格项
 *
 * @author huanghy
 * @version 6.1
 */
public interface SpecificationItemService {

    /**
     * 规格项过滤
     *
     * @param specificationItems 规格项
     */
    void filter(List<SpecificationItem> specificationItems);

}