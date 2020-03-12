/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import net.mall.dao.CartDao;
import net.mall.entity.Cart;

/**
 * Dao - 购物车
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

    @Override
    public void deleteExpired() {
        String cartItemJpql = "delete from CartItem cartItem where cartItem.cart.id in (select cart.id from Cart cart where cart.expire is not null and cart.expire <= :now)";
        String cartJpql = "delete from Cart cart where cart.expire is not null and cart.expire <= :now";
        Date now = new Date();
        entityManager.createQuery(cartItemJpql).setParameter("now", now).executeUpdate();
        entityManager.createQuery(cartJpql).setParameter("now", now).executeUpdate();
    }

}