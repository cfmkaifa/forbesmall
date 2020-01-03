/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import net.mall.util.JsonUtils;

/**
 * Entity - 商家
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Business extends User {

	private static final long serialVersionUID = 8136306384949521709L;

	/**
	 * 受限制商家权限
	 */
	public static final Set<String> RESTRICT_BUSINESS_PERMISSIONS = new HashSet<>();

	/**
	 * 正常商家权限
	 */
	public static final Set<String> NORMAL_BUSINESS_PERMISSIONS = new HashSet<>();

	/**
	 * 商家普通注册项值属性个数
	 */
	public static final int COMMON_ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

	/**
	 * 商家注册项值属性名称前缀
	 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	static {

		RESTRICT_BUSINESS_PERMISSIONS.add("business:index");
		RESTRICT_BUSINESS_PERMISSIONS.add("business:storeRegister");
		RESTRICT_BUSINESS_PERMISSIONS.add("business:storePayment");
		RESTRICT_BUSINESS_PERMISSIONS.add("business:storeReapply");

		NORMAL_BUSINESS_PERMISSIONS.add("business:index");
		NORMAL_BUSINESS_PERMISSIONS.add("business:product");
		NORMAL_BUSINESS_PERMISSIONS.add("business:stock");
		NORMAL_BUSINESS_PERMISSIONS.add("business:productNotify");
		NORMAL_BUSINESS_PERMISSIONS.add("business:consultation");
		NORMAL_BUSINESS_PERMISSIONS.add("business:review");
		NORMAL_BUSINESS_PERMISSIONS.add("business:order");
		NORMAL_BUSINESS_PERMISSIONS.add("business:print");
		NORMAL_BUSINESS_PERMISSIONS.add("business:deliveryTemplate");
		NORMAL_BUSINESS_PERMISSIONS.add("business:deliveryCenter");
		NORMAL_BUSINESS_PERMISSIONS.add("business:aftersales");
		NORMAL_BUSINESS_PERMISSIONS.add("business:storeSetting");
		NORMAL_BUSINESS_PERMISSIONS.add("business:storeProductCategory");
		NORMAL_BUSINESS_PERMISSIONS.add("business:storeProductTag");
		NORMAL_BUSINESS_PERMISSIONS.add("business:categoryApplication");
		NORMAL_BUSINESS_PERMISSIONS.add("business:storePayment");
		NORMAL_BUSINESS_PERMISSIONS.add("business:shippingMethod");
		NORMAL_BUSINESS_PERMISSIONS.add("business:areaFreightConfig");
		NORMAL_BUSINESS_PERMISSIONS.add("business:storeAdImage");
		NORMAL_BUSINESS_PERMISSIONS.add("business:aftersalesSetting");
		NORMAL_BUSINESS_PERMISSIONS.add("business:promotionPlugin");
		NORMAL_BUSINESS_PERMISSIONS.add("business:promotion");
		NORMAL_BUSINESS_PERMISSIONS.add("business:coupon");
		NORMAL_BUSINESS_PERMISSIONS.add("business:businessDeposit");
		NORMAL_BUSINESS_PERMISSIONS.add("business:businessCash");
		NORMAL_BUSINESS_PERMISSIONS.add("business:profile");
		NORMAL_BUSINESS_PERMISSIONS.add("business:password");
		NORMAL_BUSINESS_PERMISSIONS.add("business:message");
		NORMAL_BUSINESS_PERMISSIONS.add("business:messageGroup");
		NORMAL_BUSINESS_PERMISSIONS.add("business:instantMessage");
		NORMAL_BUSINESS_PERMISSIONS.add("business:orderStatistic");
		NORMAL_BUSINESS_PERMISSIONS.add("business:fundStatistic");
		NORMAL_BUSINESS_PERMISSIONS.add("business:productRanking");
	}

	/**
	 * 用户名
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Pattern.List({ @Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$") })
	@Column(nullable = false, updatable = false)
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(groups = Save.class)
	@Length(min = 4, max = 20)
	@Transient
	private String password;

	/**
	 * 加密密码
	 */
	@Column(nullable = false)
	private String encodedPassword;

	/**
	 * E-mail
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false)
	private String email;

	/**
	 * 手机
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$")
	@Column(nullable = false)
	private String mobile;

	/**
	 * 余额
	 */
	@Column(precision = 27, scale = 12)
	private BigDecimal balance;

	/**
	 * 冻结金额
	 */
	@Column(precision = 27, scale = 12)
	private BigDecimal frozenAmount;

	/**
	 * 名称
	 */
	@Length(max = 200)
	private String name;

	/**
	 * 电话
	 */
	@Length(max = 200)
	private String phone;

	/**
	 * 营业执照号
	 */
	@Length(max = 200)
	private String licenseNumber;

	/**
	 * 营业执照号图片
	 */
	@Length(max = 200)
	private String licenseImage;

	/**
	 * 法人代表姓名
	 */
	@Length(max = 200)
	private String legalPerson;

	/**
	 * 法人代表身份证
	 */
	@Length(max = 200)
	private String idCard;

	/**
	 * 法人代表身份证图片
	 */
	@Length(max = 200)
	private String idCardImage;

	/**
	 * 组织机构代码
	 */
	@Length(max = 200)
	private String organizationCode;

	/**
	 * 组织机构代码证图片
	 */
	@Length(max = 200)
	private String organizationImage;

	/**
	 * 纳税人识别号
	 */
	@Length(max = 200)
	private String identificationNumber;

	/**
	 * 税务登记证图片
	 */
	@Length(max = 200)
	private String taxImage;

	/**
	 * 银行开户名
	 */
	@Length(max = 200)
	private String bankName;

	/**
	 * 银行账号
	 */
	@Length(max = 200)
	private String bankAccount;

	/**
	 * 商家注册项值0
	 */
	@Length(max = 200)
	private String attributeValue0;

	/**
	 * 商家注册项值1
	 */
	@Length(max = 200)
	private String attributeValue1;

	/**
	 * 商家注册项值2
	 */
	@Length(max = 200)
	private String attributeValue2;

	/**
	 * 商家注册项值3
	 */
	@Length(max = 200)
	private String attributeValue3;

	/**
	 * 商家注册项值4
	 */
	@Length(max = 200)
	private String attributeValue4;

	/**
	 * 商家注册项值5
	 */
	@Length(max = 200)
	private String attributeValue5;

	/**
	 * 商家注册项值6
	 */
	@Length(max = 200)
	private String attributeValue6;

	/**
	 * 商家注册项值7
	 */
	@Length(max = 200)
	private String attributeValue7;

	/**
	 * 商家注册项值8
	 */
	@Length(max = 200)
	private String attributeValue8;

	/**
	 * 商家注册项值9
	 */
	@Length(max = 200)
	private String attributeValue9;

	/**
	 * 商家注册项值10
	 */
	@Length(max = 200)
	private String attributeValue10;

	/**
	 * 商家注册项值11
	 */
	@Length(max = 200)
	private String attributeValue11;

	/**
	 * 商家注册项值12
	 */
	@Length(max = 200)
	private String attributeValue12;

	/**
	 * 商家注册项值13
	 */
	@Length(max = 200)
	private String attributeValue13;

	/**
	 * 商家注册项值14
	 */
	@Length(max = 200)
	private String attributeValue14;

	/**
	 * 商家注册项值15
	 */
	@Length(max = 200)
	private String attributeValue15;

	/**
	 * 商家注册项值16
	 */
	@Length(max = 200)
	private String attributeValue16;

	/**
	 * 商家注册项值17
	 */
	@Length(max = 200)
	private String attributeValue17;

	/**
	 * 商家注册项值18
	 */
	@Length(max = 200)
	private String attributeValue18;

	/**
	 * 商家注册项值19
	 */
	@Length(max = 200)
	private String attributeValue19;
	
	
	/**
	 * 邮编
	 */
	@Length(max = 200)
	private String zipCode;
	
	
	/**
	 * 地址
	 */
	@Length(max = 200)
	private String address;

	/**
	 * 安全密匙
	 */
	@Embedded
	private SafeKey safeKey;

	/**
	 * 店铺
	 */
	@OneToOne(mappedBy = "business", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Store store;

	/**
	 * 提现
	 */
	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<BusinessCash> businessCashs = new HashSet<>();

	/**
	 * 商家预存款记录
	 */
	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<BusinessDepositLog> businessDepositLogs = new HashSet<>();

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
		if (password != null) {
			setEncodedPassword(DigestUtils.md5Hex(password));
		}
	}

	/**
	 * 获取加密密码
	 * 
	 * @return 加密密码
	 */
	public String getEncodedPassword() {
		return encodedPassword;
	}

	/**
	 * 设置加密密码
	 * 
	 * @param encodedPassword
	 *            加密密码
	 */
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取余额
	 * 
	 * @return 余额
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * 设置余额
	 * 
	 * @param balance
	 *            余额
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 获取冻结金额
	 * 
	 * @return 冻结金额
	 */
	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	/**
	 * 设置冻结金额
	 * 
	 * @param frozenAmount
	 *            冻结金额
	 */
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

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
	 * 获取电话
	 * 
	 * @return 电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取营业执照号
	 * 
	 * @return 营业执照号
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * 设置营业执照号
	 * 
	 * @param licenseNumber
	 *            营业执照号
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	/**
	 * 获取营业执照号图片
	 * 
	 * @return 营业执照号图片
	 */
	public String getLicenseImage() {
		return licenseImage;
	}

	/**
	 * 设置营业执照号图片
	 * 
	 * @param licenseImage
	 *            营业执照号图片
	 */
	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}

	/**
	 * 获取法人代表姓名
	 * 
	 * @return 法人代表姓名
	 */
	public String getLegalPerson() {
		return legalPerson;
	}

	/**
	 * 设置法人代表姓名
	 * 
	 * @param legalPerson
	 *            法人代表姓名
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * 获取法人代表身份证
	 * 
	 * @return 法人代表身份证
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * 设置法人代表身份证
	 * 
	 * @param idCard
	 *            法人代表身份证
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * 获取法人代表身份证图片
	 * 
	 * @return 法人代表身份证图片
	 */
	public String getIdCardImage() {
		return idCardImage;
	}

	/**
	 * 设置法人代表身份证图片
	 * 
	 * @param idCardImage
	 *            法人代表身份证图片
	 */
	public void setIdCardImage(String idCardImage) {
		this.idCardImage = idCardImage;
	}

	/**
	 * 获取组织机构代码
	 * 
	 * @return 组织机构代码
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * 设置组织机构代码
	 * 
	 * @param organizationCode
	 *            组织机构代码
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * 获取组织机构代码证图片
	 * 
	 * @return 组织机构代码证图片
	 */
	public String getOrganizationImage() {
		return organizationImage;
	}

	/**
	 * 设置组织机构代码证图片
	 * 
	 * @param organizationImage
	 *            组织机构代码证图片
	 */
	public void setOrganizationImage(String organizationImage) {
		this.organizationImage = organizationImage;
	}

	/**
	 * 获取纳税人识别号
	 * 
	 * @return 纳税人识别号
	 */
	public String getIdentificationNumber() {
		return identificationNumber;
	}

	/**
	 * 设置纳税人识别号
	 * 
	 * @param identificationNumber
	 *            纳税人识别号
	 */
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	/**
	 * 获取税务登记证图片
	 * 
	 * @return 税务登记证图片
	 */
	public String getTaxImage() {
		return taxImage;
	}

	/**
	 * 设置税务登记证图片
	 * 
	 * @param taxImage
	 *            税务登记证图片
	 */
	public void setTaxImage(String taxImage) {
		this.taxImage = taxImage;
	}

	/**
	 * 获取银行开户名
	 * 
	 * @return 银行开户名
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 设置银行开户名
	 * 
	 * @param bankName
	 *            银行开户名
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 获取银行账号
	 * 
	 * @return 银行账号
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * 设置银行账号
	 * 
	 * @param bankAccount
	 *            银行账号
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 获取商家注册项值0
	 * 
	 * @return 商家注册项值0
	 */
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置商家注册项值0
	 * 
	 * @param attributeValue0
	 *            商家注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取商家注册项值1
	 * 
	 * @return 商家注册项值1
	 */
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置商家注册项值1
	 * 
	 * @param attributeValue1
	 *            商家注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取商家注册项值2
	 * 
	 * @return 商家注册项值2
	 */
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置商家注册项值2
	 * 
	 * @param attributeValue2
	 *            商家注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取商家注册项值3
	 * 
	 * @return 商家注册项值3
	 */
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置商家注册项值3
	 * 
	 * @param attributeValue3
	 *            商家注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取商家注册项值4
	 * 
	 * @return 商家注册项值4
	 */
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置商家注册项值4
	 * 
	 * @param attributeValue4
	 *            商家注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取商家注册项值5
	 * 
	 * @return 商家注册项值5
	 */
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置商家注册项值5
	 * 
	 * @param attributeValue5
	 *            商家注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取商家注册项值6
	 * 
	 * @return 商家注册项值6
	 */
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置商家注册项值6
	 * 
	 * @param attributeValue6
	 *            商家注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取商家注册项值7
	 * 
	 * @return 商家注册项值7
	 */
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置商家注册项值7
	 * 
	 * @param attributeValue7
	 *            商家注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取商家注册项值8
	 * 
	 * @return 商家注册项值8
	 */
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置商家注册项值8
	 * 
	 * @param attributeValue8
	 *            商家注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取商家注册项值9
	 * 
	 * @return 商家注册项值9
	 */
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置商家注册项值9
	 * 
	 * @param attributeValue9
	 *            商家注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取商家注册项值10
	 * 
	 * @return 商家注册项值10
	 */
	public String getAttributeValue10() {
		return attributeValue10;
	}

	/**
	 * 设置商家注册项值10
	 * 
	 * @param attributeValue10
	 *            商家注册项值10
	 */
	public void setAttributeValue10(String attributeValue10) {
		this.attributeValue10 = attributeValue10;
	}

	/**
	 * 获取商家注册项值11
	 * 
	 * @return 商家注册项值11
	 */
	public String getAttributeValue11() {
		return attributeValue11;
	}

	/**
	 * 设置商家注册项值11
	 * 
	 * @param attributeValue11
	 *            商家注册项值11
	 */
	public void setAttributeValue11(String attributeValue11) {
		this.attributeValue11 = attributeValue11;
	}

	/**
	 * 获取商家注册项值12
	 * 
	 * @return 商家注册项值12
	 */
	public String getAttributeValue12() {
		return attributeValue12;
	}

	/**
	 * 设置商家注册项值12
	 * 
	 * @param attributeValue12
	 *            商家注册项值12
	 */
	public void setAttributeValue12(String attributeValue12) {
		this.attributeValue12 = attributeValue12;
	}

	/**
	 * 获取商家注册项值13
	 * 
	 * @return 商家注册项值13
	 */
	public String getAttributeValue13() {
		return attributeValue13;
	}

	/**
	 * 设置商家注册项值13
	 * 
	 * @param attributeValue13
	 *            商家注册项值13
	 */
	public void setAttributeValue13(String attributeValue13) {
		this.attributeValue13 = attributeValue13;
	}

	/**
	 * 获取商家注册项值14
	 * 
	 * @return 商家注册项值14
	 */
	public String getAttributeValue14() {
		return attributeValue14;
	}

	/**
	 * 设置商家注册项值14
	 * 
	 * @param attributeValue14
	 *            商家注册项值14
	 */
	public void setAttributeValue14(String attributeValue14) {
		this.attributeValue14 = attributeValue14;
	}

	/**
	 * 获取商家注册项值15
	 * 
	 * @return 商家注册项值15
	 */
	public String getAttributeValue15() {
		return attributeValue15;
	}

	/**
	 * 设置商家注册项值15
	 * 
	 * @param attributeValue15
	 *            商家注册项值15
	 */
	public void setAttributeValue15(String attributeValue15) {
		this.attributeValue15 = attributeValue15;
	}

	/**
	 * 获取商家注册项值16
	 * 
	 * @return 商家注册项值16
	 */
	public String getAttributeValue16() {
		return attributeValue16;
	}

	/**
	 * 设置商家注册项值16
	 * 
	 * @param attributeValue16
	 *            商家注册项值16
	 */
	public void setAttributeValue16(String attributeValue16) {
		this.attributeValue16 = attributeValue16;
	}

	/**
	 * 获取商家注册项值17
	 * 
	 * @return 商家注册项值17
	 */
	public String getAttributeValue17() {
		return attributeValue17;
	}

	/**
	 * 设置商家注册项值17
	 * 
	 * @param attributeValue17
	 *            商家注册项值17
	 */
	public void setAttributeValue17(String attributeValue17) {
		this.attributeValue17 = attributeValue17;
	}

	/**
	 * 获取商家注册项值18
	 * 
	 * @return 商家注册项值18
	 */
	public String getAttributeValue18() {
		return attributeValue18;
	}

	/**
	 * 设置商家注册项值18
	 * 
	 * @param attributeValue18
	 *            商家注册项值18
	 */
	public void setAttributeValue18(String attributeValue18) {
		this.attributeValue18 = attributeValue18;
	}

	/**
	 * 获取商家注册项值19
	 * 
	 * @return 商家注册项值19
	 */
	public String getAttributeValue19() {
		return attributeValue19;
	}

	/**
	 * 设置商家注册项值19
	 * 
	 * @param attributeValue19
	 *            商家注册项值19
	 */
	public void setAttributeValue19(String attributeValue19) {
		this.attributeValue19 = attributeValue19;
	}

	/**
	 * 获取安全密匙
	 * 
	 * @return 安全密匙
	 */
	public SafeKey getSafeKey() {
		return safeKey;
	}

	/**
	 * 设置安全密匙
	 * 
	 * @param safeKey
	 *            安全密匙
	 */
	public void setSafeKey(SafeKey safeKey) {
		this.safeKey = safeKey;
	}

	/**
	 * 获取店铺
	 * 
	 * @return 店铺
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * 设置店铺
	 * 
	 * @param store
	 *            店铺
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * 获取提现
	 * 
	 * @return 提现
	 */
	public Set<BusinessCash> getBusinessCashs() {
		return businessCashs;
	}

	/**
	 * 设置提现
	 * 
	 * @param businessCashs
	 *            提现
	 */
	public void setBusinessCashs(Set<BusinessCash> businessCashs) {
		this.businessCashs = businessCashs;
	}

	/**
	 * 获取商家预存款记录
	 * 
	 * @return 商家预存款记录
	 */
	public Set<BusinessDepositLog> getBusinessDepositLogs() {
		return businessDepositLogs;
	}

	/**
	 * 设置商家预存款记录
	 * 
	 * @param businessDepositLogs
	 *            商家预存款记录
	 */
	public void setBusinessDepositLogs(Set<BusinessDepositLog> businessDepositLogs) {
		this.businessDepositLogs = businessDepositLogs;
	}
	
	
	

	/** 
	 * @return zipCode 
	 */
	public String getZipCode() {
		return zipCode;
	}

	/** 
	 * @param zipCode 要设置的 zipCode 
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/** 
	 * @return address 
	 */
	public String getAddress() {
		return address;
	}

	/** 
	 * @param address 要设置的 address 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	

	/**
	 * 获取商家注册项值
	 * 
	 * @param businessAttribute
	 *            商家注册项
	 * @return 商家注册项值
	 */
	@Transient
	public Object getAttributeValue(BusinessAttribute businessAttribute) {
		if (businessAttribute == null || businessAttribute.getType() == null) {
			return null;
		}
		switch (businessAttribute.getType()) {
		case NAME:
			return getName();
		case LICENSE_NUMBER:
			return getLicenseNumber();
		case LICENSE_IMAGE:
			return getLicenseImage();
		case LEGAL_PERSON:
			return getLegalPerson();
		case ID_CARD:
			return getIdCard();
		case ID_CARD_IMAGE:
			return getIdCardImage();
		case ADDRESS:
			return getAddress();
		case ZIP_CODE:
			return getZipCode();
		case PHONE:
			return getPhone();
		case ORGANIZATION_CODE:
			return getOrganizationCode();
		case ORGANIZATION_IMAGE:
			return getOrganizationImage();
		case IDENTIFICATION_NUMBER:
			return getIdentificationNumber();
		case TAX_IMAGE:
			return getTaxImage();
		case BANK_NAME:
			return getBankName();
		case BANK_ACCOUNT:
			return getBankAccount();
		case TEXT:
		case SELECT:
		case IMAGE:
		case DATE:
			if (businessAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + businessAttribute.getPropertyIndex();
					return PropertyUtils.getProperty(this, propertyName);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case CHECKBOX:
			if (businessAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + businessAttribute.getPropertyIndex();
					String propertyValue = String.valueOf(PropertyUtils.getProperty(this, propertyName));
					if (StringUtils.isNotEmpty(propertyValue)) {
						return JsonUtils.toObject(propertyValue, List.class);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		return null;
	}

	/**
	 * 设置商家注册项值
	 * 
	 * @param businessAttribute
	 *            商家注册项
	 * @param businessAttributeValue
	 *            商家注册项值
	 */
	@Transient
	public void setAttributeValue(BusinessAttribute businessAttribute, Object businessAttributeValue) {
		if (businessAttribute == null || businessAttribute.getType() == null) {
			return;
		}
		switch (businessAttribute.getType()) {
		case NAME:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setName((String) businessAttributeValue);
			}
			break;
		case LICENSE_NUMBER:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setLicenseNumber((String) businessAttributeValue);
			}
			break;
		case LICENSE_IMAGE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setLicenseImage((String) businessAttributeValue);
			}
			break;
		case LEGAL_PERSON:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setLegalPerson((String) businessAttributeValue);
			}
			break;
		case ID_CARD:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setIdCard((String) businessAttributeValue);
			}
			break;
		case ID_CARD_IMAGE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setIdCardImage((String) businessAttributeValue);
			}
			break;
		case ADDRESS:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setAddress((String) businessAttributeValue);
			}
			break;
		case ZIP_CODE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setZipCode((String) businessAttributeValue);
			}
			break;
		case PHONE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setPhone((String) businessAttributeValue);
			}
			break;
		case ORGANIZATION_CODE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setOrganizationCode((String) businessAttributeValue);
			}
			break;
		case ORGANIZATION_IMAGE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setOrganizationImage((String) businessAttributeValue);
			}
			break;
		case IDENTIFICATION_NUMBER:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setIdentificationNumber((String) businessAttributeValue);
			}
			break;
		case TAX_IMAGE:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setTaxImage((String) businessAttributeValue);
			}
			break;
		case BANK_NAME:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setBankName((String) businessAttributeValue);
			}
			break;
		case BANK_ACCOUNT:
			if (businessAttributeValue instanceof String || businessAttributeValue == null) {
				setBankAccount((String) businessAttributeValue);
			}
			break;
		case TEXT:
		case SELECT:
			if ((businessAttributeValue instanceof String || businessAttributeValue == null) && businessAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + businessAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, businessAttributeValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case IMAGE:
		case DATE:
			if ((businessAttributeValue instanceof String || businessAttributeValue == null) && businessAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + businessAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, businessAttributeValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		case CHECKBOX:
			if ((businessAttributeValue instanceof Collection || businessAttributeValue == null) && businessAttribute.getPropertyIndex() != null) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + businessAttribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, businessAttributeValue != null ? JsonUtils.toJson(businessAttributeValue) : null);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
	}

	/**
	 * 移除所有商家注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setName(null);
		setLicenseNumber(null);
		setLicenseImage(null);
		setLegalPerson(null);
		setIdCard(null);
		setIdCardImage(null);
		setPhone(null);
		setOrganizationCode(null);
		setOrganizationImage(null);
		setIdentificationNumber(null);
		setTaxImage(null);
		setBankName(null);
		setBankAccount(null);
		for (int i = 0; i < COMMON_ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 获取可用余额
	 * 
	 * @return 可用余额
	 */
	@Transient
	public BigDecimal getAvailableBalance() {
		if (getBalance() == null || getFrozenAmount() == null) {
			return BigDecimal.ZERO;
		}
		return getBalance().compareTo(getFrozenAmount()) < 0 ? BigDecimal.ZERO : getBalance().subtract(getFrozenAmount());
	}

	@Override
	@Transient
	public String getDisplayName() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getPrincipal() {
		return getUsername();
	}

	@Override
	@Transient
	public Object getCredentials() {
		return getPassword();
	}

	@Override
	@Transient
	public boolean isValidCredentials(Object credentials) {
		return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword());
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setUsername(StringUtils.lowerCase(getUsername()));
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

}