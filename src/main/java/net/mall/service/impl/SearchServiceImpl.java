/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.service.SearchService;

/**
 * Service - 搜索
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SearchServiceImpl implements SearchService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void index(Class<?> type) {
		index(type, true);
	}

	@Override
	public void index(Class<?> type, boolean purgeAll) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer(type).purgeAllOnStart(purgeAll).start();
	}

}