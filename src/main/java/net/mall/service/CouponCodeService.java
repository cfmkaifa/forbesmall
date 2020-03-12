/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Coupon;
import net.mall.entity.CouponCode;
import net.mall.entity.Member;

/**
 * Service - 优惠码
 *
 * @author huanghy
 * @version 6.1
 */
public interface CouponCodeService extends BaseService<CouponCode, Long> {

    /**
     * 判断优惠码是否存在
     *
     * @param code 号码(忽略大小写)
     * @return 优惠码是否存在
     */
    boolean codeExists(String code);

    /**
     * 根据号码查找优惠码
     *
     * @param code 号码(忽略大小写)
     * @return 优惠码，若不存在则返回null
     */
    CouponCode findByCode(String code);

    /**
     * 生成优惠码
     *
     * @param coupon 优惠券
     * @param member 会员
     * @return 优惠码
     */
    CouponCode generate(Coupon coupon, Member member);

    /**
     * 生成优惠码
     *
     * @param coupon 优惠券
     * @param member 会员
     * @param count  数量
     * @return 优惠码
     */
    List<CouponCode> generate(Coupon coupon, Member member, Integer count);

    /**
     * 兑换优惠码
     *
     * @param coupon 优惠券
     * @param member 会员
     * @return 优惠码
     */
    CouponCode exchange(Coupon coupon, Member member);

    /**
     * 查找优惠码分页
     *
     * @param member   会员
     * @param pageable 分页信息
     * @return 优惠码分页
     */
    Page<CouponCode> findPage(Member member, Pageable pageable);

    /**
     * 查找优惠码数量
     *
     * @param coupon     优惠券
     * @param member     会员
     * @param hasBegun   是否已开始
     * @param hasExpired 是否已过期
     * @param isUsed     是否已使用
     * @return 优惠码数量
     */
    Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed);

}