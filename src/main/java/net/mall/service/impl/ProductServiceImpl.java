/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Setting;
import net.mall.dao.AttributeDao;
import net.mall.dao.BrandDao;
import net.mall.dao.ProductCategoryDao;
import net.mall.dao.ProductDao;
import net.mall.dao.ProductTagDao;
import net.mall.dao.PromotionDao;
import net.mall.dao.SkuDao;
import net.mall.dao.SnDao;
import net.mall.dao.StockLogDao;
import net.mall.dao.StoreDao;
import net.mall.dao.StoreProductCategoryDao;
import net.mall.dao.StoreProductTagDao;
import net.mall.entity.Attribute;
import net.mall.entity.Brand;
import net.mall.entity.Product;
import net.mall.entity.Product.Type;
import net.mall.entity.ProductCategory;
import net.mall.entity.ProductTag;
import net.mall.entity.Promotion;
import net.mall.entity.Sku;
import net.mall.entity.Sn;
import net.mall.entity.Specification.Sample;
import net.mall.entity.SpecificationItem;
import net.mall.entity.StockLog;
import net.mall.entity.Store;
import net.mall.entity.StoreProductCategory;
import net.mall.entity.StoreProductTag;
import net.mall.service.ProductService;
import net.mall.service.SearchService;
import net.mall.service.SpecificationValueService;
import net.mall.util.SystemUtils;

/**
 * Service - 商品
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private SearchService searchService;
    @Inject
    private CacheManager cacheManager;
    @Inject
    private ProductDao productDao;
    @Inject
    private SkuDao skuDao;
    @Inject
    private SnDao snDao;
    @Inject
    private ProductCategoryDao productCategoryDao;
    @Inject
    private StoreProductCategoryDao storeProductCategoryDao;
    @Inject
    private BrandDao brandDao;
    @Inject
    private PromotionDao promotionDao;
    @Inject
    private ProductTagDao productTagDao;
    @Inject
    private StoreProductTagDao storeProductTagDao;
    @Inject
    private AttributeDao attributeDao;
    @Inject
    private StockLogDao stockLogDao;
    @Inject
    private StoreDao storeDao;
    @Inject
    private SpecificationValueService specificationValueService;

    @Override
    @Transactional(readOnly = true)
    public boolean snExists(String sn) {
        return productDao.exists("sn", sn, true);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findBySn(String sn) {
        return productDao.find("sn", sn, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findList(Product.Type type, Store store, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
                                  BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders) {
        return productDao.findList(type, store, productCategory, storeProductCategory, brand, promotion, productTag, storeProductTag, attributeValueMap, startPrice, endPrice, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, hasPromotion, orderType, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "product", condition = "#useCache")
    public List<Product> findList(Product.Type type, Long storeId, Long productCategoryId, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, Long storeProductTagId, Map<Long, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
                                  Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
        Store store = storeDao.find(storeId);
        if (storeId != null && store == null) {
            return Collections.emptyList();
        }
        ProductCategory productCategory = productCategoryDao.find(productCategoryId);
        if (productCategoryId != null && productCategory == null) {
            return Collections.emptyList();
        }
        StoreProductCategory storeProductCategory = storeProductCategoryDao.find(storeProductCategoryId);
        if (storeProductCategoryId != null && storeProductCategory == null) {
            return Collections.emptyList();
        }
        Brand brand = brandDao.find(brandId);
        if (brandId != null && brand == null) {
            return Collections.emptyList();
        }
        Promotion promotion = promotionDao.find(promotionId);
        if (promotionId != null && promotion == null) {
            return Collections.emptyList();
        }
        ProductTag productTag = productTagDao.find(productTagId);
        if (productTagId != null && productTag == null) {
            return Collections.emptyList();
        }
        StoreProductTag storeProductTag = storeProductTagDao.find(storeProductTagId);
        if (storeProductTagId != null && storeProductTag == null) {
            return Collections.emptyList();
        }
        Map<Attribute, String> map = new HashMap<>();
        if (attributeValueMap != null) {
            for (Map.Entry<Long, String> entry : attributeValueMap.entrySet()) {
                Attribute attribute = attributeDao.find(entry.getKey());
                if (attribute != null) {
                    map.put(attribute, entry.getValue());
                }
            }
        }
        if (MapUtils.isNotEmpty(attributeValueMap) && MapUtils.isEmpty(map)) {
            return Collections.emptyList();
        }
        return productDao.findList(type, store, productCategory, storeProductCategory, brand, promotion, productTag, storeProductTag, map, startPrice, endPrice, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, hasPromotion, orderType, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findList(Product.RankingType rankingType, Store store, Integer count) {
        return productDao.findList(rankingType, store, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findPage(Product.Type type, Integer method,boolean isSample, Store.Type storeType, Store store, ProductCategory productCategory, StoreProductCategory storeProductCategory, Brand brand, Promotion promotion, ProductTag productTag, StoreProductTag storeProductTag, Map<Attribute, String> attributeValueMap,
                                  BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Pageable pageable) {
        return productDao.findPage(type, method,isSample, storeType, store, productCategory, storeProductCategory, brand, promotion, productTag, storeProductTag, attributeValueMap, startPrice, endPrice, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, hasPromotion, orderType, pageable);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Product> search(String keyword, Product.Type type, Store.Type storeType, Store store, Boolean isOutOfStock, Boolean isStockAlert, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable) {
        if (StringUtils.isEmpty(keyword)) {
            return Page.emptyPage(pageable);
        }

        if (pageable == null) {
            pageable = new Pageable();
        }

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();

        Query snPhraseQuery = queryBuilder.phrase().onField("sn").sentence(keyword).createQuery();
        Query introductionPhraseQuery = queryBuilder.phrase().withSlop(3).onField("introduction").sentence(keyword).createQuery();
        Query isMarketablePhraseQuery = queryBuilder.phrase().onField("isMarketable").sentence("true").createQuery();
        Query isListPhraseQuery = queryBuilder.phrase().onField("isList").sentence("true").createQuery();
        Query isActivePhraseQuery = queryBuilder.phrase().onField("isActive").sentence("true").createQuery();

        BooleanJunction<?> junction = queryBuilder.bool().must(isMarketablePhraseQuery).must(isListPhraseQuery).must(isActivePhraseQuery);
        BooleanJunction<?> subJunction = queryBuilder.bool().should(snPhraseQuery).should(introductionPhraseQuery);
        if (keyword.length() < 5) {
            Query namePhraseQuery = queryBuilder.phrase().withSlop(3).onField("name").sentence(keyword).createQuery();
            Query keywordPhraseQuery = queryBuilder.phrase().withSlop(3).onField("keyword").sentence(keyword).createQuery();
            subJunction.should(namePhraseQuery).should(keywordPhraseQuery);
        } else {
            Query nameFuzzyQuery = queryBuilder.keyword().fuzzy().onField("name").ignoreAnalyzer().matching(keyword).createQuery();
            Query keywordFuzzyQuery = queryBuilder.keyword().fuzzy().onField("keyword").ignoreAnalyzer().matching(keyword).createQuery();
            subJunction.should(nameFuzzyQuery).should(keywordFuzzyQuery);
        }
        junction.must(subJunction.createQuery());

        if (type != null) {
            Query typePhraseQuery = queryBuilder.phrase().onField("type").sentence(String.valueOf(type)).createQuery();
            junction.must(typePhraseQuery);
        }
        if (storeType != null) {
            Query storeTypePhraseQuery = queryBuilder.phrase().onField("store.type").sentence(String.valueOf(storeType)).createQuery();
            junction.must(storeTypePhraseQuery);
        }
        if (store != null) {
            Query storePhraseQuery = queryBuilder.phrase().onField("store.id").sentence(String.valueOf(store.getId())).createQuery();
            junction.must(storePhraseQuery);
        }
        if (isOutOfStock != null) {
            Query isOutOfStockPhraseQuery = queryBuilder.phrase().onField("skus.isOutOfStock").sentence(String.valueOf(isOutOfStock)).createQuery();
            junction.must(isOutOfStockPhraseQuery);
        }
        if (isStockAlert != null) {
            Query isStockAlertPhraseQuery = queryBuilder.phrase().onField("skus.isStockAlert").sentence(String.valueOf(isStockAlert)).createQuery();
            junction.must(isStockAlertPhraseQuery);
        }
        if (startPrice != null && endPrice != null) {
            Query priceRangeQuery = queryBuilder.range().onField("price").from(startPrice.doubleValue()).to(endPrice.doubleValue()).createQuery();
            junction.must(priceRangeQuery);
        } else if (startPrice != null) {
            Query priceRangeQuery = queryBuilder.range().onField("price").above(startPrice.doubleValue()).createQuery();
            junction.must(priceRangeQuery);
        } else if (endPrice != null) {
            Query priceRangeQuery = queryBuilder.range().onField("price").below(endPrice.doubleValue()).createQuery();
            junction.must(priceRangeQuery);
        }
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(junction.createQuery(), Product.class);

        SortField[] sortFields = null;
        if (orderType != null) {
            switch (orderType) {
                case TOP_DESC:
                    sortFields = new SortField[]{new SortField("isTop", SortField.Type.STRING, true), new SortField(null, SortField.Type.SCORE), new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case PRICE_ASC:
                    sortFields = new SortField[]{new SortField("price", SortField.Type.DOUBLE, false), new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case PRICE_DESC:
                    sortFields = new SortField[]{new SortField("price", SortField.Type.DOUBLE, true), new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case SALES_DESC:
                    sortFields = new SortField[]{new SortField("sales", SortField.Type.LONG, true), new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case SCORE_DESC:
                    sortFields = new SortField[]{new SortField("score", SortField.Type.FLOAT, true), new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
                case DATE_DESC:
                    sortFields = new SortField[]{new SortField("createdDate", SortField.Type.LONG, true)};
                    break;
            }
        } else {
            sortFields = new SortField[]{new SortField("isTop", SortField.Type.STRING, true), new SortField(null, SortField.Type.SCORE), new SortField("createdDate", SortField.Type.LONG, true)};
        }
        fullTextQuery.setSort(new Sort(sortFields));
        fullTextQuery.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
        fullTextQuery.setMaxResults(pageable.getPageSize());
        return new Page<>(fullTextQuery.getResultList(), fullTextQuery.getResultSize(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(Product.Type type, Store store, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert) {
        return productDao.count(type, store, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert);
    }

    @Override
    public Long count(Type type, Long storeId, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert) {
        Store store = storeDao.find(storeId);
        if (storeId != null && store == null) {
            return 0L;
        }
        return productDao.count(type, store, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert);
    }

    @Override
    public long viewHits(Long id) {
        Assert.notNull(id, "[Assertion failed] - id is required; it must not be null");

        Ehcache cache = cacheManager.getEhcache(Product.HITS_CACHE_NAME);
        cache.acquireWriteLockOnKey(id);
        try {
            Element element = cache.get(id);
            Long hits;
            if (element != null) {
                hits = (Long) element.getObjectValue() + 1;
            } else {
                Product product = productDao.find(id);
                if (product == null) {
                    return 0L;
                }
                hits = product.getHits() + 1;
            }
            cache.put(new Element(id, hits));
            return hits;
        } finally {
            cache.releaseWriteLockOnKey(id);
        }
    }

    @Override
    public void addHits(Product product, long amount) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.state(amount >= 0, "[Assertion failed] - amount must be equal or greater than 0");

        if (amount == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(productDao.getLockMode(product))) {
            productDao.flush();
            productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
        }

        Calendar nowCalendar = Calendar.getInstance();
        Calendar weekHitsCalendar = DateUtils.toCalendar(product.getWeekHitsDate());
        Calendar monthHitsCalendar = DateUtils.toCalendar(product.getMonthHitsDate());
        if (nowCalendar.get(Calendar.YEAR) > weekHitsCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekHitsCalendar.get(Calendar.WEEK_OF_YEAR)) {
            product.setWeekHits(amount);
        } else {
            product.setWeekHits(product.getWeekHits() + amount);
        }
        if (nowCalendar.get(Calendar.YEAR) > monthHitsCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthHitsCalendar.get(Calendar.MONTH)) {
            product.setMonthHits(amount);
        } else {
            product.setMonthHits(product.getMonthHits() + amount);
        }
        product.setHits(product.getHits() + amount);
        product.setWeekHitsDate(new Date());
        product.setMonthHitsDate(new Date());
        productDao.flush();
    }

    @Override
    public void addSales(Product product, long amount) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.state(amount >= 0, "[Assertion failed] - amount must be equal or greater than 0");

        if (amount == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(productDao.getLockMode(product))) {
            productDao.flush();
            productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
        }

        Calendar nowCalendar = Calendar.getInstance();
        Calendar weekSalesCalendar = DateUtils.toCalendar(product.getWeekSalesDate());
        Calendar monthSalesCalendar = DateUtils.toCalendar(product.getMonthSalesDate());
        if (nowCalendar.get(Calendar.YEAR) > weekSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekSalesCalendar.get(Calendar.WEEK_OF_YEAR)) {
            product.setWeekSales(amount);
        } else {
            product.setWeekSales(product.getWeekSales() + amount);
        }
        if (nowCalendar.get(Calendar.YEAR) > monthSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthSalesCalendar.get(Calendar.MONTH)) {
            product.setMonthSales(amount);
        } else {
            product.setMonthSales(product.getMonthSales() + amount);
        }
        product.setSales(product.getSales() + amount);
        product.setWeekSalesDate(new Date());
        product.setMonthSalesDate(new Date());
        productDao.flush();
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product create(Product product, Sku sku) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(product.isNew(), "[Assertion failed] - product must be new");
        Assert.notNull(product.getType(), "[Assertion failed] - product type is required; it must not be null");
        Assert.isTrue(!product.hasSpecification(), "[Assertion failed] - product must not have specification");
        Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
        Assert.isTrue(sku.isNew(), "[Assertion failed] - sku must be new");
        Assert.state(!sku.hasSpecification(), "[Assertion failed] - sku must not have specification");

        switch (product.getType()) {
            case GENERAL:
                sku.setExchangePoint(0L);
                break;
            case EXCHANGE:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                product.setPromotions(null);
                break;
            case GIFT:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                sku.setExchangePoint(0L);
                product.setPromotions(null);
                break;
        }
        if (sku.getMarketPrice() == null) {
            sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
        }
        if (sku.getRewardPoint() == null) {
            sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
        } else {
            long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
            sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
        }
        sku.setSample(Sample.NO);
        sku.setAllocatedStock(0);
        sku.setIsDefault(true);
        sku.setProduct(product);
        sku.setSpecificationValues(null);
        sku.setCartItems(null);
        sku.setOrderItems(null);
        sku.setOrderShippingItems(null);
        sku.setProductNotifies(null);
        sku.setStockLogs(null);

        product.setPrice(sku.getPrice());
        product.setCost(sku.getCost());
        product.setMarketPrice(sku.getMarketPrice());
        product.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
        product.setIsActive(true);
        product.setScore(0F);
        product.setTotalScore(0L);
        product.setScoreCount(0L);
        product.setHits(0L);
        product.setWeekHits(0L);
        product.setMonthHits(0L);
        product.setSales(0L);
        product.setWeekSales(0L);
        product.setMonthSales(0L);
        product.setWeekHitsDate(new Date());
        product.setMonthHitsDate(new Date());
        product.setWeekSalesDate(new Date());
        product.setMonthSalesDate(new Date());
        product.setSpecificationItems(null);
        product.setReviews(null);
        product.setConsultations(null);
        product.setProductFavorites(null);
        product.setSkus(null);
        setValue(product);
        productDao.persist(product);

        setValue(sku);
        skuDao.persist(sku);
        stockIn(sku);

        return product;
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product create(Product product, List<Sku> skus) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(product.isNew(), "[Assertion failed] - product must be new");
        Assert.notNull(product.getType(), "[Assertion failed] - product type is required; it must not be null");
        Assert.isTrue(product.hasSpecification(), "[Assertion failed] - product must have specification");
        Assert.notEmpty(skus, "[Assertion failed] - skus must not be empty: it must contain at least 1 element");

        final List<SpecificationItem> specificationItems = product.getSpecificationItems();
        if (CollectionUtils.exists(skus, new Predicate() {
            private Set<List<Integer>> set = new HashSet<>();

            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku == null || !sku.isNew() || !sku.hasSpecification() || !set.add(sku.getSpecificationValueIds()) || !specificationValueService.isValid(specificationItems, sku.getSpecificationValues());
            }
        })) {
            throw new IllegalArgumentException();
        }

        Sku defaultSku = (Sku) CollectionUtils.find(skus, new Predicate() {
            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku != null && sku.getIsDefault();
            }
        });
        if (defaultSku == null) {
            defaultSku = skus.get(0);
            defaultSku.setIsDefault(true);
            defaultSku.setSample(Sample.NO);
        }

        for (Sku sku : skus) {
            switch (product.getType()) {
                case GENERAL:
                    sku.setExchangePoint(0L);
                    break;
                case EXCHANGE:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    product.setPromotions(null);
                    break;
                case GIFT:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    sku.setExchangePoint(0L);
                    product.setPromotions(null);
                    break;
            }
            if (sku.getMarketPrice() == null) {
                sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
            }
            if (sku.getRewardPoint() == null) {
                sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
            } else {
                long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
                sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
            }
            if (sku != defaultSku) {
                sku.setIsDefault(false);
            }
            sku.setSample(Sample.NO);
            sku.setAllocatedStock(0);
            sku.setProduct(product);
            sku.setCartItems(null);
            sku.setOrderItems(null);
            sku.setOrderShippingItems(null);
            sku.setProductNotifies(null);
            sku.setStockLogs(null);
        }

        product.setPrice(defaultSku.getPrice());
        product.setCost(defaultSku.getCost());
        product.setMarketPrice(defaultSku.getMarketPrice());
        product.setMaxCommission(defaultSku.getMaxCommission().compareTo(defaultSku.getPrice()) > 0 ? BigDecimal.ZERO : defaultSku.getMaxCommission());
        product.setIsActive(true);
        product.setScore(0F);
        product.setTotalScore(0L);
        product.setScoreCount(0L);
        product.setHits(0L);
        product.setWeekHits(0L);
        product.setMonthHits(0L);
        product.setSales(0L);
        product.setWeekSales(0L);
        product.setMonthSales(0L);
        product.setWeekHitsDate(new Date());
        product.setMonthHitsDate(new Date());
        product.setWeekSalesDate(new Date());
        product.setMonthSalesDate(new Date());
        product.setReviews(null);
        product.setConsultations(null);
        product.setProductFavorites(null);
        product.setSkus(null);
        setValue(product);
        productDao.persist(product);
        for (Sku sku : skus) {
            setValue(sku);
            sku.setSample(Sample.NO);
            skuDao.persist(sku);
            stockIn(sku);
        }
        return product;
    }


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
    @Override
    @CacheEvict(value = {"product"}, allEntries = true)
    public Product modifySample(Product product, Sku sku, Store currentStore) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(!product.hasSpecification(), "[Assertion failed] - product must not have specification");
        Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
        Assert.state(!sku.hasSpecification(), "[Assertion failed] - sku must not have specification");
        Product pProduct = productDao.find(product.getId());
        switch (pProduct.getType()) {
            case GENERAL:
                sku.setExchangePoint(0L);
                break;
            case EXCHANGE:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                product.setPromotions(null);
                break;
            case GIFT:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                sku.setExchangePoint(0L);
                product.setPromotions(null);
                break;
        }
        if (sku.getMarketPrice() == null) {
            sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
        }
        if (sku.getRewardPoint() == null) {
            sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
        } else {
            long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
            sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
        }
        sku.setSample(Sample.YES);
        if (pProduct.hasSpecification()) {
            for (Sku pSku : pProduct.getSkus()) {
                if (Sample.YES.equals(pSku.getSample())) {
                    skuDao.remove(pSku);
                }
            }
            if (sku.getStock() == null) {
                throw new IllegalArgumentException();
            }
            sku.setSample(Sample.YES);
            setValue(sku);
            skuDao.persist(sku);
            stockIn(sku);
        }
        List<SpecificationItem> specificationItems = product.getSpecificationItems();
        if (null != specificationItems
                && specificationItems.size() > 0) {
            pProduct.setSpecificationItems(specificationItems);
        }
        pProduct.setSampleStore(currentStore);
        return pProduct;
    }


    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product modify(Product product, Sku sku) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(!product.isNew(), "[Assertion failed] - product must not be new");
        Assert.isTrue(!product.hasSpecification(), "[Assertion failed] - product must not have specification");
        Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
        Assert.isTrue(sku.isNew(), "[Assertion failed] - sku must be new");
        Assert.state(!sku.hasSpecification(), "[Assertion failed] - sku must not have specification");
        Product pProduct = productDao.find(product.getId());
        switch (pProduct.getType()) {
            case GENERAL:
                sku.setExchangePoint(0L);
                break;
            case EXCHANGE:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                product.setPromotions(null);
                break;
            case GIFT:
                sku.setPrice(BigDecimal.ZERO);
                sku.setMaxCommission(BigDecimal.ZERO);
                sku.setRewardPoint(0L);
                sku.setExchangePoint(0L);
                product.setPromotions(null);
                break;
        }
        if (sku.getMarketPrice() == null) {
            sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
        }
        if (sku.getRewardPoint() == null) {
            sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
        } else {
            long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
            sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
        }
        sku.setAllocatedStock(0);
        sku.setIsDefault(true);
        sku.setProduct(pProduct);
        sku.setSpecificationValues(null);
        sku.setCartItems(null);
        sku.setOrderItems(null);
        sku.setOrderShippingItems(null);
        sku.setProductNotifies(null);
        sku.setStockLogs(null);
        sku.setSample(Sample.NO);
        if (pProduct.hasSpecification()) {
            for (Sku pSku : pProduct.getSkus()) {
                if (Sample.NO.equals(pSku.getSample())) {
                    skuDao.remove(pSku);
                }
            }
            if (sku.getStock() == null) {
                throw new IllegalArgumentException();
            }
            setValue(sku);
            sku.setSample(Sample.NO);
            skuDao.persist(sku);
            stockIn(sku);
        } else {
            Sku defaultSku = pProduct.getDefaultSku();
            defaultSku.setPrice(sku.getPrice());
            defaultSku.setCost(sku.getCost());
            defaultSku.setMarketPrice(sku.getMarketPrice());
            defaultSku.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
            defaultSku.setRewardPoint(sku.getRewardPoint());
            defaultSku.setExchangePoint(sku.getExchangePoint());
            defaultSku.setSample(Sample.NO);
        }
        product.setPrice(sku.getPrice());
        product.setCost(sku.getCost());
        product.setMarketPrice(sku.getMarketPrice());
        product.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
        setValue(product);
        copyProperties(product, pProduct, "sn", "type", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "reviews", "consultations", "productFavorites", "skus", "store");

        return pProduct;
    }


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
    @Override
    @CacheEvict(value = {"product"}, allEntries = true)
    public Product modifySample(Product product, List<Sku> skus, Store currentStore) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(product.hasSpecification(), "[Assertion failed] - product must have specification");
        Assert.notEmpty(skus, "[Assertion failed] - skus must not be empty: it must contain at least 1 element");
        final List<SpecificationItem> specificationItems = product.getSpecificationItems();
        /***删除过滤掉
         * **/
        skus.removeIf(sku -> CollectionUtils.isEmpty(sku.getSpecificationValues()));
        if (CollectionUtils.exists(skus, new Predicate() {
            private Set<List<Integer>> set = new HashSet<>();

            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku == null || !sku.isNew() || !sku.hasSpecification() || !set.add(sku.getSpecificationValueIds()) || !specificationValueService.isValid(specificationItems, sku.getSpecificationValues());
            }
        })) {
            throw new IllegalArgumentException();
        }
        Sku defaultSku = (Sku) CollectionUtils.find(skus, new Predicate() {
            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku != null && sku.getIsDefault();
            }
        });
        if (defaultSku == null) {
            defaultSku = skus.get(0);
            defaultSku.setIsDefault(true);
            defaultSku.setSample(Sample.YES);
        }
        Product pProduct = productDao.find(product.getId());
        for (Sku sku : skus) {
            switch (pProduct.getType()) {
                case GENERAL:
                    sku.setExchangePoint(0L);
                    break;
                case EXCHANGE:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    product.setPromotions(null);
                    break;
                case GIFT:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    sku.setExchangePoint(0L);
                    product.setPromotions(null);
                    break;
            }
            if (sku.getMarketPrice() == null) {
                sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
            }
            if (sku.getRewardPoint() == null) {
                sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
            } else {
                long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
                sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
            }
            if (sku != defaultSku) {
                sku.setIsDefault(false);
            }
            sku.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
            sku.setAllocatedStock(0);
            sku.setProduct(pProduct);
            sku.setCartItems(null);
            sku.setOrderItems(null);
            sku.setOrderShippingItems(null);
            sku.setProductNotifies(null);
            sku.setStockLogs(null);
            sku.setSample(Sample.YES);
        }
        if (pProduct.hasSpecification()) {
            for (Sku pSku : pProduct.getSkus()) {
                if ((Sample.YES.equals(pSku.getSample()))
                        && !exists(skus, pSku.getSpecificationValueIds())) {
                    skuDao.remove(pSku);
                }
            }
            for (Sku sku : skus) {
                Sku pSku = find(pProduct.getSkus(), sku.getSpecificationValueIds());
                if (pSku != null) {
                    pSku.setPrice(sku.getPrice());
                    pSku.setCost(sku.getCost());
                    pSku.setMarketPrice(sku.getMarketPrice());
                    pSku.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
                    pSku.setRewardPoint(sku.getRewardPoint());
                    pSku.setExchangePoint(sku.getExchangePoint());
                    pSku.setIsDefault(sku.getIsDefault());
                    pSku.setTotalUnit(sku.getTotalUnit());
                    pSku.setUnit(sku.getUnit());
                    pSku.setSpecificationValues(sku.getSpecificationValues());
                    pSku.setSample(Sample.YES);
                } else {
                    if (sku.getStock() == null) {
                        throw new IllegalArgumentException();
                    }
                    setValue(sku);
                    sku.setSample(Sample.YES);
                    skuDao.persist(sku);
                    stockIn(sku);
                }
            }
        } else {
            if (Sample.YES.equals(pProduct.getDefaultSku().getSample())) {
                skuDao.remove(pProduct.getDefaultSku());
            }
            for (Sku sku : skus) {
                if (sku.getStock() == null) {
                    throw new IllegalArgumentException();
                }
                sku.setSample(Sample.YES);
                setValue(sku);
                skuDao.persist(sku);
                stockIn(sku);
            }
        }
        pProduct.setSpecificationItems(specificationItems);
        pProduct.setSampleStore(currentStore);
        return pProduct;
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product modify(Product product, List<Sku> skus) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");
        Assert.isTrue(!product.isNew(), "[Assertion failed] - product must not be new");
        Assert.isTrue(product.hasSpecification(), "[Assertion failed] - product must have specification");
        Assert.notEmpty(skus, "[Assertion failed] - skus must not be empty: it must contain at least 1 element");
        final List<SpecificationItem> specificationItems = product.getSpecificationItems();
        /***删除过滤掉
         * **/
        skus.removeIf(sku -> CollectionUtils.isEmpty(sku.getSpecificationValues()));
        if (CollectionUtils.exists(skus, new Predicate() {
            private Set<List<Integer>> set = new HashSet<>();

            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku == null || !sku.isNew() || !sku.hasSpecification() || !set.add(sku.getSpecificationValueIds()) || !specificationValueService.isValid(specificationItems, sku.getSpecificationValues());
            }
        })) {
            throw new IllegalArgumentException();
        }

        Sku defaultSku = (Sku) CollectionUtils.find(skus, new Predicate() {
            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku != null && sku.getIsDefault();
            }
        });
        if (defaultSku == null) {
            defaultSku = skus.get(0);
            defaultSku.setIsDefault(true);
        }
        defaultSku.setSample(Sample.NO);
        Product pProduct = productDao.find(product.getId());
        for (Sku sku : skus) {
            switch (pProduct.getType()) {
                case GENERAL:
                    sku.setExchangePoint(0L);
                    break;
                case EXCHANGE:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    product.setPromotions(null);
                    break;
                case GIFT:
                    sku.setPrice(BigDecimal.ZERO);
                    sku.setMaxCommission(BigDecimal.ZERO);
                    sku.setRewardPoint(0L);
                    sku.setExchangePoint(0L);
                    product.setPromotions(null);
                    break;
            }
            if (sku.getMarketPrice() == null) {
                sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
            }
            if (sku.getRewardPoint() == null) {
                sku.setRewardPoint(calculateDefaultRewardPoint(sku.getPrice()));
            } else {
                long maxRewardPoint = calculateMaxRewardPoint(sku.getPrice());
                sku.setRewardPoint(sku.getRewardPoint() > maxRewardPoint ? maxRewardPoint : sku.getRewardPoint());
            }
            if (sku != defaultSku) {
                sku.setIsDefault(false);
            }
            sku.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
            sku.setAllocatedStock(0);
            sku.setProduct(pProduct);
            sku.setCartItems(null);
            sku.setOrderItems(null);
            sku.setOrderShippingItems(null);
            sku.setProductNotifies(null);
            sku.setStockLogs(null);
            sku.setSample(Sample.NO);
        }

        if (pProduct.hasSpecification()) {
            for (Sku pSku : pProduct.getSkus()) {
                if (Sample.NO.equals(pSku.getSample())
                        && !exists(skus, pSku.getSpecificationValueIds())) {
                    skuDao.remove(pSku);
                }
            }
            for (Sku sku : skus) {
                Sku pSku = find(pProduct.getSkus(), sku.getSpecificationValueIds());
                if (pSku != null) {
                    pSku.setPrice(sku.getPrice());
                    pSku.setCost(sku.getCost());
                    pSku.setMarketPrice(sku.getMarketPrice());
                    pSku.setMaxCommission(sku.getMaxCommission().compareTo(sku.getPrice()) > 0 ? BigDecimal.ZERO : sku.getMaxCommission());
                    pSku.setRewardPoint(sku.getRewardPoint());
                    pSku.setExchangePoint(sku.getExchangePoint());
                    pSku.setIsDefault(sku.getIsDefault());
                    pSku.setSample(Sample.NO);
                    pSku.setTotalUnit(sku.getTotalUnit());
                    pSku.setUnit(sku.getUnit());
                    pSku.setSpecificationValues(sku.getSpecificationValues());
                } else {
                    if (sku.getStock() == null) {
                        throw new IllegalArgumentException();
                    }
                    sku.setSample(Sample.NO);
                    setValue(sku);
                    skuDao.persist(sku);
                    stockIn(sku);
                }
            }
        } else {
            skuDao.remove(pProduct.getDefaultSku());
            for (Sku sku : skus) {
                if (sku.getStock() == null) {
                    throw new IllegalArgumentException();
                }
                sku.setSample(Sample.NO);
                setValue(sku);
                skuDao.persist(sku);
                stockIn(sku);
            }
        }

        product.setPrice(defaultSku.getPrice());
        product.setCost(defaultSku.getCost());
        product.setMarketPrice(defaultSku.getMarketPrice());
        product.setMaxCommission(defaultSku.getMaxCommission().compareTo(defaultSku.getPrice()) > 0 ? BigDecimal.ZERO : defaultSku.getMaxCommission());
        setValue(product);
        copyProperties(product, pProduct, "sn", "type", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "reviews", "consultations", "productFavorites", "skus", "store");

        return pProduct;
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void refreshExpiredStoreProductActive() {
        productDao.refreshExpiredStoreProductActive();
        searchService.index(Product.class);
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void refreshActive(Store store) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

        productDao.refreshActive(store);
        searchService.index(Product.class);
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void shelves(Long[] ids) {
        Assert.notEmpty(ids, "[Assertion failed] - ids must not be empty: it must contain at least 1 element");

        productDao.shelves(ids);
        searchService.index(Product.class);
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void shelf(Long[] ids) {
        Assert.notEmpty(ids, "[Assertion failed] - ids must not be empty: it must contain at least 1 element");

        productDao.shelf(ids);
        searchService.index(Product.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product save(Product product) {
        return super.save(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product update(Product product) {
        return super.update(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public Product update(Product product, String... ignoreProperties) {
        return super.update(product, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void delete(Product product) {
        super.delete(product);
    }

    /**
     * 设置商品值
     *
     * @param product 商品
     */
    private void setValue(Product product) {
        if (product == null) {
            return;
        }

        if (product.isNew()) {
            if (StringUtils.isEmpty(product.getSn())) {
                String sn;
                do {
                    sn = snDao.generate(Sn.Type.PRODUCT);
                } while (snExists(sn));
                product.setSn(sn);
            }
        }
    }

    /**
     * 设置SKU值
     *
     * @param sku SKU
     */
    private void setValue(Sku sku) {
        if (sku == null) {
            return;
        }

        if (sku.isNew()) {
            Product product = sku.getProduct();
            if (product != null && StringUtils.isNotEmpty(product.getSn())) {
                String sn;
                int i = sku.hasSpecification() ? 1 : 0;
                do {
                    sn = product.getSn() + (i == 0 ? StringUtils.EMPTY : "_" + i);
                    i++;
                } while (skuDao.exists("sn", sn, true));
                sku.setSn(sn);
            }
        }
    }

    /**
     * 计算默认市场价
     *
     * @param price 价格
     * @return 默认市场价
     */
    private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
        Assert.notNull(price, "[Assertion failed] - price is required; it must not be null");

        Setting setting = SystemUtils.getSetting();
        Double defaultMarketPriceScale = setting.getDefaultMarketPriceScale();
        return defaultMarketPriceScale != null ? setting.setScale(price.multiply(new BigDecimal(String.valueOf(defaultMarketPriceScale)))) : BigDecimal.ZERO;
    }

    /**
     * 计算默认赠送积分
     *
     * @param price 价格
     * @return 默认赠送积分
     */
    private long calculateDefaultRewardPoint(BigDecimal price) {
        Assert.notNull(price, "[Assertion failed] - price is required; it must not be null");

        Setting setting = SystemUtils.getSetting();
        Double defaultPointScale = setting.getDefaultPointScale();
        return defaultPointScale != null ? price.multiply(new BigDecimal(String.valueOf(defaultPointScale))).longValue() : 0L;
    }

    /**
     * 计算最大赠送积分
     *
     * @param price 价格
     * @return 最大赠送积分
     */
    private long calculateMaxRewardPoint(BigDecimal price) {
        Assert.notNull(price, "[Assertion failed] - price is required; it must not be null");

        Setting setting = SystemUtils.getSetting();
        Double maxPointScale = setting.getMaxPointScale();
        return maxPointScale != null ? price.multiply(new BigDecimal(String.valueOf(maxPointScale))).longValue() : 0L;
    }

    /**
     * 根据规格值ID查找SKU
     *
     * @param skus                  SKU
     * @param specificationValueIds 规格值ID
     * @return SKU
     */
    private Sku find(Collection<Sku> skus, final List<Integer> specificationValueIds) {
        if (CollectionUtils.isEmpty(skus) || CollectionUtils.isEmpty(specificationValueIds)) {
            return null;
        }

        return (Sku) CollectionUtils.find(skus, new Predicate() {
            public boolean evaluate(Object object) {
                Sku sku = (Sku) object;
                return sku != null && sku.getSpecificationValueIds() != null && sku.getSpecificationValueIds().equals(specificationValueIds);
            }
        });
    }

    /**
     * 根据规格值ID判断SKU是否存在
     *
     * @param skus                  SKU
     * @param specificationValueIds 规格值ID
     * @return SKU是否存在
     */
    private boolean exists(Collection<Sku> skus, final List<Integer> specificationValueIds) {
        return find(skus, specificationValueIds) != null;
    }

    /**
     * 入库
     *
     * @param sku SKU
     */
    private void stockIn(Sku sku) {
        if (sku == null || sku.getStock() == null || sku.getStock() <= 0) {
            return;
        }

        StockLog stockLog = new StockLog();
        stockLog.setType(StockLog.Type.STOCK_IN);
        stockLog.setInQuantity(sku.getStock());
        stockLog.setOutQuantity(0);
        stockLog.setStock(sku.getStock());
        stockLog.setMemo(null);
        stockLog.setSku(sku);
        stockLogDao.persist(stockLog);
    }


    /***
     *
     * @param curentDate
     * @param productId
     */
    public void modifyProduct(Date curentDate, Long productId){
        productDao.modifyProduct(curentDate,productId);
    }

}