/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.ParameterValue;

/**
 * Service - 参数值
 *
 * @author huanghy
 * @version 6.1
 */
public interface ParameterValueService {

    /**
     * 参数值过滤
     *
     * @param parameterValues 参数值
     */
    void filter(List<ParameterValue> parameterValues);

}