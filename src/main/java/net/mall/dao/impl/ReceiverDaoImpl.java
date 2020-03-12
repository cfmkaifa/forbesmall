/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.ReceiverDao;
import net.mall.entity.Member;
import net.mall.entity.Receiver;

/**
 * Dao - 收货地址
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class ReceiverDaoImpl extends BaseDaoImpl<Receiver, Long> implements ReceiverDao {

    @Override
    public Receiver findDefault(Member member) {
        if (member == null) {
            return null;
        }
        try {
            String jpql = "select receiver from Receiver receiver where receiver.member = :member and receiver.isDefault = true";
            return entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).getSingleResult();
        } catch (NoResultException e) {
            try {
                String jpql = "select receiver from Receiver receiver where receiver.member = :member order by receiver.lastModifiedDate desc";
                return entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).setMaxResults(1).getSingleResult();
            } catch (NoResultException e1) {
                return null;
            }
        }
    }

    @Override
    public List<Receiver> findList(Member member) {
        if (member == null) {
            return Collections.emptyList();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Receiver> criteriaQuery = criteriaBuilder.createQuery(Receiver.class);
        Root<Receiver> root = criteriaQuery.from(Receiver.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
        return super.findList(criteriaQuery);
    }

    @Override
    public Page<Receiver> findPage(Member member, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Receiver> criteriaQuery = criteriaBuilder.createQuery(Receiver.class);
        Root<Receiver> root = criteriaQuery.from(Receiver.class);
        criteriaQuery.select(root);
        if (member != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
        }
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public void clearDefault(Member member) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");

        String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true";
        entityManager.createQuery(jpql).setParameter("member", member).executeUpdate();
    }

    @Override
    public void clearDefault(Member member, Receiver exclude) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(exclude, "[Assertion failed] - exclude is required; it must not be null");

        String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true and receiver != :exclude";
        entityManager.createQuery(jpql).setParameter("member", member).setParameter("exclude", exclude).executeUpdate();
    }

}