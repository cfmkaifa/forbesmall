/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 文章分类
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class ArticleCategory extends OrderedEntity<Long> {

	private static final long serialVersionUID = -5132652107151648662L;

	/**
	 * 树路径分隔符
	 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 路径
	 */
	private static final String PATH = "/article/list/%d";
	
	
	
	/**
	 * 类型
	 */
	public enum Type {

		/**
		 *新闻资讯
		 */
		NEWS,

		/**平台说明
		 */
		INST
	}
	
	
	/****
	 * Subscribe概要说明：是否需要订阅
	 * @author Huanghy
	 */
	public enum Subscribe {
		/**否**/
		NO,
		/**是**/
		YES
	}
	
	/**
	 *是否需要订阅
	 */
	@NotNull
	@Column(nullable = false)
	private ArticleCategory.Subscribe subscribe;
	
	
	
	/**
	 * 文章类型
	 */
	@NotNull
	@Column(nullable = false)
	private ArticleCategory.Type type;
	
	
	
	
	/**
	 * 超时时间
	 */
	private Integer timeout;
	
	
	/***周
	 */
	@Column(name="week_sub_fee")
	private BigDecimal weekSubFee;
	
	
	/***月
	 */
	@Column(name="month_sub_fee")
	private BigDecimal monthSubFee;
	
	
	/***季
	 */
	@Column(name="quarter_sub_fee")
	private BigDecimal quarterSubFee;
	
	
	/***年
	 */
	@Column(name="year_sub_fee")
	private BigDecimal yearSubFee;

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 页面标题
	 */
	@Length(max = 200)
	private String seoTitle;

	/**
	 * 页面关键词
	 */
	@Length(max = 200)
	private String seoKeywords;

	/**
	 * 页面描述
	 */
	@Length(max = 200)
	private String seoDescription;

	/**
	 * 树路径
	 */
	@Column(nullable = false)
	private String treePath;

	/**
	 * 层级
	 */
	@Column(nullable = false)
	private Integer grade;

	/**
	 * 上级分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ArticleCategory parent;

	/**
	 * 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	private Set<ArticleCategory> children = new HashSet<>();

	/**
	 * 文章
	 */
	@OneToMany(mappedBy = "articleCategory", fetch = FetchType.LAZY)
	private Set<Article> articles = new HashSet<>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级分类
	 * 
	 * @return 上级分类
	 */
	public ArticleCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(ArticleCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	public Set<ArticleCategory> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<ArticleCategory> children) {
		this.children = children;
	}

	/**
	 * 获取文章
	 * 
	 * @return 文章
	 */
	public Set<Article> getArticles() {
		return articles;
	}

	/**
	 * 设置文章
	 * 
	 * @param articles
	 *            文章
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	
	
	

	/** 
	 * @return subscribe 
	 */
	public ArticleCategory.Subscribe getSubscribe() {
		return subscribe;
	}

	/** 
	 * @param subscribe 要设置的 subscribe 
	 */
	public void setSubscribe(ArticleCategory.Subscribe subscribe) {
		this.subscribe = subscribe;
	}

	/** 
	 * @return type 
	 */
	public ArticleCategory.Type getType() {
		return type;
	}

	/** 
	 * @param type 要设置的 type 
	 */
	public void setType(ArticleCategory.Type type) {
		this.type = type;
	}

	
	
	
	/** 
	 * @return weekSubFee 
	 */
	public BigDecimal getWeekSubFee() {
		return weekSubFee;
	}

	/** 
	 * @param weekSubFee 要设置的 weekSubFee 
	 */
	public void setWeekSubFee(BigDecimal weekSubFee) {
		this.weekSubFee = weekSubFee;
	}

	/** 
	 * @return monthSubFee 
	 */
	public BigDecimal getMonthSubFee() {
		return monthSubFee;
	}

	/** 
	 * @param monthSubFee 要设置的 monthSubFee 
	 */
	public void setMonthSubFee(BigDecimal monthSubFee) {
		this.monthSubFee = monthSubFee;
	}

	/** 
	 * @return quarterSubFee 
	 */
	public BigDecimal getQuarterSubFee() {
		return quarterSubFee;
	}

	/** 
	 * @param quarterSubFee 要设置的 quarterSubFee 
	 */
	public void setQuarterSubFee(BigDecimal quarterSubFee) {
		this.quarterSubFee = quarterSubFee;
	}

	/** 
	 * @return yearSubFee 
	 */
	public BigDecimal getYearSubFee() {
		return yearSubFee;
	}

	/** 
	 * @param yearSubFee 要设置的 yearSubFee 
	 */
	public void setYearSubFee(BigDecimal yearSubFee) {
		this.yearSubFee = yearSubFee;
	}

	/** 
	 * @return timeout 
	 */
	public Integer getTimeout() {
		return timeout;
	}

	/** 
	 * @param timeout 要设置的 timeout 
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return String.format(ArticleCategory.PATH, getId());
	}

	/**
	 * 获取所有上级分类ID
	 * 
	 * @return 所有上级分类ID
	 */
	@Transient
	public Long[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = Long.valueOf(parentIds[i]);
		}
		return result;
	}

	/**
	 * 获取所有上级分类
	 * 
	 * @return 所有上级分类
	 */
	@Transient
	public List<ArticleCategory> getParents() {
		List<ArticleCategory> parents = new ArrayList<>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级分类
	 * 
	 * @param parents
	 *            上级分类
	 * @param articleCategory
	 *            文章分类
	 */
	private void recursiveParents(List<ArticleCategory> parents, ArticleCategory articleCategory) {
		if (articleCategory == null) {
			return;
		}
		ArticleCategory parent = articleCategory.getParent();
		if (parent != null) {
			parents.add(0, parent);
			recursiveParents(parents, parent);
		}
	}

}