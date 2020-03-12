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
import java.util.Set;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.MemberDepositLog;
import net.mall.entity.PointLog;
import net.mall.security.AuthenticationProvider;

/**
 * Service - 会员
 *
 * @author huanghy
 * @version 6.1
 */
public interface MemberService extends BaseService<Member, Long>, AuthenticationProvider {

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名(忽略大小写)
     * @return 用户名是否存在
     */
    boolean usernameExists(String username);

    /**
     * 根据用户名查找会员
     *
     * @param username 用户名(忽略大小写)
     * @return 会员，若不存在则返回null
     */
    Member findByUsername(String username);

    /**
     * 判断E-mail是否存在
     *
     * @param email E-mail(忽略大小写)
     * @return E-mail是否存在
     */
    boolean emailExists(String email);

    /**
     * 判断E-mail是否唯一
     *
     * @param id    ID
     * @param email E-mail(忽略大小写)
     * @return E-mail是否唯一
     */
    boolean emailUnique(Long id, String email);

    /**
     * 根据E-mail查找会员
     *
     * @param email E-mail(忽略大小写)
     * @return 会员，若不存在则返回null
     */
    Member findByEmail(String email);

    /**
     * 判断手机是否存在
     *
     * @param mobile 手机(忽略大小写)
     * @return 手机是否存在
     */
    boolean mobileExists(String mobile);

    /**
     * 判断手机是否唯一
     *
     * @param id     ID
     * @param mobile 手机(忽略大小写)
     * @return 手机是否唯一
     */
    boolean mobileUnique(Long id, String mobile);

    /**
     * 通过名称查找会员
     *
     * @param keyword  关键词
     * @param excludes 排除会员
     * @param count    数量
     * @return 会员
     */
    List<Member> search(String keyword, Set<Member> excludes, Integer count);

    /**
     * 根据手机查找会员
     *
     * @param mobile 手机(忽略大小写)
     * @return 会员，若不存在则返回null
     */
    Member findByMobile(String mobile);

    /**
     * 查找会员分页
     *
     * @param rankingType 排名类型
     * @param pageable    分页信息
     * @return 会员分页
     */
    Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

    /**
     * 增加余额
     *
     * @param member 会员
     * @param amount 值
     * @param type   类型
     * @param memo   备注
     */
    void addBalance(Member member, BigDecimal amount, MemberDepositLog.Type type, String memo);

    /**
     * 增加冻结金额
     *
     * @param member 会员
     * @param amount 值
     */
    void addFrozenAmount(Member member, BigDecimal amount);

    /**
     * 增加积分
     *
     * @param member 会员
     * @param amount 值
     * @param type   类型
     * @param memo   备注
     */
    void addPoint(Member member, long amount, PointLog.Type type, String memo);

    /**
     * 增加消费金额
     *
     * @param member 会员
     * @param amount 值
     */
    void addAmount(Member member, BigDecimal amount);

    /**
     * 查询总余额
     *
     * @return 总余额
     */
    BigDecimal totalBalance();

    /**
     * 查询会员数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 会员数量
     */
    long count(Date beginDate, Date endDate);

}