/*
 *
 *
 *
 *
 */
package net.mall.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import net.mall.entity.Product;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Dao - SKU
 *
 * @author huanghy
 * @version 6.1
 */
public interface SkuDao extends BaseDao<Sku, Long> {

    /**
     * 通过编号、名称查找SKU
     *
     * @param store    店铺
     * @param type     类型
     * @param keyword  关键词
     * @param excludes 排除SKU
     * @param count    数量
     * @return SKU
     */
    List<Sku> search(Store store, Product.Type type, String keyword, Set<Sku> excludes, Integer count);

    /**
     * 查找SKU
     *
     * @param store  店铺
     * @param type   类型
     * @param matchs 匹配SKU
     * @param count  数量
     * @return SKU
     */
    List<Sku> findList(Store store, Product.Type type, Set<Sku> matchs, Integer count);


    /***
     * 更新SKU费用
     * @param groupPrice
     * @param skuSn
     */
    void modifySkuGroupPrice(BigDecimal groupPrice, Boolean group, String skuSn);
}