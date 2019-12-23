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
import java.util.Set;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.MemberAttribute;

/**
 * Dao - 会员
 * 
 * @author huanghy
 * @version 6.1
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

	/**
	 * 查询会员数量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	Long count(Date beginDate, Date endDate);

	/**
	 * 通过名称查找会员
	 * 
	 * @param keyword
	 *            关键词
	 * @param excludes
	 *            排除会员
	 * @param count
	 *            数量
	 * @return 会员
	 */
	List<Member> search(String keyword, Set<Member> excludes, Integer count);

	/**
	 * 清除会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);

	/**
	 * 查询总余额
	 * 
	 * @return 总余额
	 */
	BigDecimal totalBalance();

	/**
	 * 查询冻结总额
	 * 
	 * @return 冻结总额
	 */
	BigDecimal frozenTotalAmount();

}