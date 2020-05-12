/*
 *
 *
 *
 *
 */
package net.mall.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.*;

/**
 * Dao - 商品
 *
 * @author huanghy
 * @version 6.1
 */
public interface ProductDao extends BaseDao<Product, Long> {

    /**
     * 查找商品
     *
     * @param type                 类型
     * @param store                店铺
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param brand                品牌
     * @param promotion            促销
     * @param productTag           商品标签
     * @param storeProductTag      店铺商品标签
     * @param attributeValueMap    属性值Map
     * @param startPrice           最低价格
     * @param endPrice             最高价格
     * @param isMarketable         是否上架
     * @param isList               是否列出
     * @param isTop                是否置顶
     * @param isActive             是否有效
     * @param isOutOfStock         是否缺货
     * @param isStockAlert         是否库存警告
     * @param hasPromotion         是否存在促销
     * @param orderType            排序类型
     * @param count                数量
     * @param filters              筛选
     * @param orders               排序
     * @return 商品
     */
    List<Product> findList(Product.Type type, Store store, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
                           BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找商品
     *
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param isMarketable         是否上架
     * @param isActive             是否有效
     * @param beginDate            起始日期
     * @param endDate              结束日期
     * @param first                起始记录
     * @param count                数量
     * @return 商品
     */
    List<Product> findList(ProductCategory productCategory, StoreProductCategory storeProductCategory, Boolean isMarketable, Boolean isActive, Date beginDate, Date endDate, Integer first, Integer count);

    /**
     * 查找商品
     *
     * @param rankingType 排名类型
     * @param store       店铺
     * @param count       数量
     * @return 商品
     */
    List<Product> findList(Product.RankingType rankingType, Store store, Integer count);

    /**
     * 查找商品分页
     *
     * @param type                 类型
     * @param storeType            店铺类型
     * @param store                店铺
     * @param productCategory      商品分类
     * @param storeProductCategory 店铺商品分类
     * @param brand                品牌
     * @param promotion            促销
     * @param productTag           商品标签
     * @param storeProductTag      店铺商品标签
     * @param attributeValueMap    属性值Map
     * @param startPrice           最低价格
     * @param endPrice             最高价格
     * @param isMarketable         是否上架
     * @param isList               是否列出
     * @param isTop                是否置顶
     * @param isActive             是否有效
     * @param isOutOfStock         是否缺货
     * @param isStockAlert         是否库存警告
     * @param hasPromotion         是否存在促销
     * @param orderType            排序类型
     * @param pageable             分页信息
     * @return 商品分页
     */
    Page<Product> findPage(Product.Type type, Integer method, boolean isSample, Store.Type storeType, Store store, Member member, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap,
                           BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Pageable pageable);

    /**
     * 查询商品数量
     *
     * @param type         类型
     * @param store        店铺
     * @param isMarketable 是否上架
     * @param isList       是否列出
     * @param isTop        是否置顶
     * @param isActive     是否有效
     * @param isOutOfStock 是否缺货
     * @param isStockAlert 是否库存警告
     * @return 商品数量
     */
    Long count(Product.Type type, Store store, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert);

    /**
     * 清除商品属性值
     *
     * @param attribute 属性
     */
    void clearAttributeValue(Attribute attribute);

    /**
     * 刷新过期店铺商品有效状态
     */
    void refreshExpiredStoreProductActive();

    /**
     * 刷新商品有效状态
     *
     * @param store 店铺
     */
    void refreshActive(Store store);

    /**
     * 上架商品
     *
     * @param ids ID
     */
    void shelves(Long[] ids);

    /**
     * 下架商品
     *
     * @param ids ID
     */
    void shelf(Long[] ids);


    /***
     * 修改产品
     * @param isGroup
     * @param proSn
     */
    void modifyProductGroupPurch(Boolean isGroup, String proSn);


    /***
     *
     * @param curentDate
     * @param productId
     */
    void modifyProduct(Date curentDate,Long productId);


    /***
     * 修改上下架
     * @param marketable
     * @param productId
     */
    public void modifyMarketable(Boolean marketable, Long productId);

    /***
     * 根据sourceID更新新产品ID
     * @param newProductId
     * @param sourceProId
     */
    void modifyNewProductIdBySourceId(Long newProductId,Long sourceProId);


    /***
     * 根据产品ID更新新产品ID
     * @param newProductId
     * @param productId
     */
    void modifyNewProductId(Long newProductId,Long productId);



    /***
     * 修改审核状态
     * @param isAudit
     * @param productId
     */
    public void modifyProductAudit(Product.ProApplyStatus isAudit, Long productId);
}