/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.MemberDao;
import net.mall.dao.ProductFavoriteDao;
import net.mall.entity.Member;
import net.mall.entity.Product;
import net.mall.entity.ProductFavorite;
import net.mall.service.ProductFavoriteService;

/**
 * Service - 商品收藏
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class ProductFavoriteServiceImpl extends BaseServiceImpl<ProductFavorite, Long> implements ProductFavoriteService {

	@Inject
	private ProductFavoriteDao productFavoriteDao;
	@Inject
	private MemberDao memberDao;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Member member, Product product) {
		return productFavoriteDao.exists(member, product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
		return productFavoriteDao.findList(member, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProductFavorite> findPage(Member member, Pageable pageable) {
		return productFavoriteDao.findPage(member, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member) {
		return productFavoriteDao.count(member);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "productFavorite", condition = "#useCache")
	public List<ProductFavorite> findList(Long memberId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}

		return productFavoriteDao.findList(member, count, filters, orders);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public ProductFavorite save(ProductFavorite productFavorite) {
		return super.save(productFavorite);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public ProductFavorite update(ProductFavorite productFavorite) {
		return super.update(productFavorite);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public ProductFavorite update(ProductFavorite productFavorite, String... ignoreProperties) {
		return super.update(productFavorite, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "productFavorite", allEntries = true)
	public void delete(ProductFavorite productFavorite) {
		super.delete(productFavorite);
	}

}