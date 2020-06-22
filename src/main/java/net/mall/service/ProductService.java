/*
 *
 *
 *
 *
 */
package net.mall.service;

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
 * Service - 商品
 *
 * @author huanghy
 * @version 6.1
 */
public interface ProductService extends BaseService<Product, Long> {

    /**
     * 判断编号是否存在
     *
     * @param sn 编号(忽略大小写)
     * @return 编号是否存在
     */
    boolean snExists(String sn);

    /**
     * 根据编号查找商品
     *
     * @param sn 编号(忽略大小写)
     * @return 商品，若不存在则返回null
     */
    Product findBySn(String sn);

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
     * @param type                   类型
     * @param storeId                店铺ID
     * @param productCategoryId      商品分类ID
     * @param storeProductCategoryId 店铺商品分类ID
     * @param brandId                品牌ID
     * @param promotionId            促销ID
     * @param productTagId           商品标签ID
     * @param storeProductTagId      店铺商品标签ID
     * @param attributeValueMap      属性值Map
     * @param startPrice             最低价格
     * @param endPrice               最高价格
     * @param isMarketable           是否上架
     * @param isList                 是否列出
     * @param isTop                  是否置顶
     * @param isActive               是否有效
     * @param isOutOfStock           是否缺货
     * @param isStockAlert           是否库存警告
     * @param hasPromotion           是否存在促销
     * @param orderType              排序类型
     * @param count                  数量
     * @param filters                筛选
     * @param orders                 排序
     * @param useCache               是否使用缓存
     * @return 商品
     */
    List<Product> findList(Product.Type type, Long storeId, Long productCategoryId, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, Long storeProductTagId, Map<Long, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
                           Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

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
     * @param type
     * @param method(0自己商品，1-代销商品) 类型
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
     * 搜索商品分页
     *
     * @param keyword      关键词
     * @param type         类型
     * @param storeType    店铺类型
     * @param store        店铺
     * @param isOutOfStock 是否缺货
     * @param isStockAlert 是否库存警告
     * @param startPrice   最低价格
     * @param endPrice     最高价格
     * @param orderType    排序类型
     * @param pageable     分页信息
     * @return 商品分页
     */
    Page<Product> search(String keyword, Product.Type type, Store.Type storeType, Store store, Boolean isOutOfStock, Boolean isStockAlert, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable);

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
     * 查询商品数量
     *
     * @param type         类型
     * @param storeId      店铺ID
     * @param isMarketable 是否上架
     * @param isList       是否列出
     * @param isTop        是否置顶
     * @param isActive     是否有效
     * @param isOutOfStock 是否缺货
     * @param isStockAlert 是否库存警告
     * @return 商品数量
     */
    Long count(Product.Type type, Long storeId, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert);

    /**
     * 查看点击数
     *
     * @param id ID
     * @return 点击数
     */
    long viewHits(Long id);

    /**
     * 增加点击数
     *
     * @param product 商品
     * @param amount  值
     */
    void addHits(Product product, long amount);

    /**
     * 增加销量
     *
     * @param product 商品
     * @param amount  值
     */
    void addSales(Product product, long amount);

    /**
     * 创建
     *
     * @param product 商品
     * @param sku     SKU
     * @return 商品
     */
    Product create(Product product, Sku sku);

    /**
     * 创建
     *
     * @param product 商品
     * @param skus    SKU
     * @return 商品
     */
    Product create(Product product, List<Sku> skus);

    /**
     * 修改
     *
     * @param product 商品
     * @param sku     SKU
     * @return 商品
     */
    Product modify(Product product, Sku sku);


    /****
     * modifySample方法慨述:修改样品规格
     * @param product
     * @param sku
     * @return Product
     * @创建人 huanghy
     * @创建时间 2019年12月24日 上午10:47:27
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    Product modifySample(Product product, Sku sku, Store currentStore);


    /***
     * modifySample方法慨述:批量添加SKU
     * @param product
     * @param skus
     * @param currentStore
     * @return Product
     * @创建人 huanghy
     * @创建时间 2019年12月24日 上午11:03:49
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    Product modifySample(Product product, List<Sku> skus, Store currentStore);


    /**
     * 修改
     *
     * @param product 商品
     * @param skus    SKU
     * @return 商品
     */
    Product modify(Product product, List<Sku> skus);

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
     *
     * @param curentDate
     * @param productId
     */
    public void modifyProduct(Date curentDate, Long productId);


    /***
     * 变成游离态
     * @param product
     * @return
     */
    boolean clearProduct(Product product);


    /***
     * 复制商品
     * @param product
     * @param skus
     * @return
     */
    public Product copyProduct(Product product, List<Sku> skus,Long productId);



    /***
     * 修改上下架
     * @param marketable
     * @param productId
     */
    public void modifyMarketable(Boolean marketable, Long productId);

    /***
     * 修改审核状态
     * @param isAudit
     * @param productId
     */
    public void modifyProductAudit(Product.ProApplyStatus isAudit, Long productId);


    /***
     * 查询快过期团购
     * @param isPurch
     * @param isAudit
     * @param isMarketable
     * @param expire
     * @return
     */
    public List<Product> searchProApply(Boolean isPurch, Product.ProApplyStatus isAudit, Boolean isMarketable, Date expire);
}