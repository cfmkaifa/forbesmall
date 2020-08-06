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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.util.JsonUtils;

/**
 * Entity - 会员
 *
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Member extends User {

    private static final long serialVersionUID = 1533130686714725835L;

    /**
     * 权限
     */
    public static final Set<String> PERMISSIONS = Collections.singleton("member");

    /**
     * 性别
     */
    public enum Gender {

        /**
         * 男
         */
        MALE,

        /**
         * 女
         */
        FEMALE
    }

    /**
     * 排名类型
     */
    public enum RankingType {

        /**
         * 积分
         */
        POINT,

        /**
         * 余额
         */
        BALANCE,

        /**
         * 消费金额
         */
        AMOUNT
    }

    /**
     * 会员注册项值属性个数
     */
    public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

    /**
     * 会员注册项值属性名称前缀
     */
    public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

    /**
     * 用户名
     */
    @JsonView(BaseView.class)
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Pattern.List({@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$"), @Pattern(regexp = "^.*[^\\d].*$")})
    @Column(nullable = false, updatable = false)
    private String username;

    /**
     * 密码
     */
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Transient
    private String password;

    /***
     * 父级ID
     */
    @Column(name = "parent_id")
    private Long parentId;

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
    @Column(nullable = true)
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
     * 积分
     */
    private Long point;

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
     * 消费金额
     */
    @Column(precision = 27, scale = 12)
    private BigDecimal amount;

    /**
     * 姓名
     */
    @Length(max = 200)
    private String name;

    /**
     * 性别
     */
    private Member.Gender gender;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 地址
     */
    @Length(max = 200)
    private String address;

    /**
     * 邮编
     */
    @Length(max = 200)
    private String zipCode;

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

    @Length(max = 200)
    @Column(name = "bank_address")
    private String bankAddress;

    /**
     * 会员注册项值0
     */
    @Length(max = 200)
    private String attributeValue0;

    /**
     * 会员注册项值1
     */
    @Length(max = 200)
    private String attributeValue1;

    /**
     * 会员注册项值2
     */
    @Length(max = 200)
    private String attributeValue2;

    /**
     * 会员注册项值3
     */
    @Length(max = 200)
    private String attributeValue3;

    /**
     * 会员注册项值4
     */
    @Length(max = 200)
    private String attributeValue4;

    /**
     * 会员注册项值5
     */
    @Length(max = 200)
    private String attributeValue5;

    /**
     * 会员注册项值6
     */
    @Length(max = 200)
    private String attributeValue6;

    /**
     * 会员注册项值7
     */
    @Length(max = 200)
    private String attributeValue7;

    /**
     * 会员注册项值8
     */
    @Length(max = 200)
    private String attributeValue8;

    /**
     * 会员注册项值9
     */
    @Length(max = 200)
    private String attributeValue9;

    /**
     * 安全密匙
     */
    @Embedded
    private SafeKey safeKey;

    /**
     * 地区
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    /**
     * 会员等级
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberRank memberRank;

    /**
     * 分销员
     */
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Distributor distributor;

    /**
     * 购物车
     */
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Cart cart;

    /**
     * 订单
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Order> orders = new HashSet<>();

    /**
     * 会员预存款记录
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<MemberDepositLog> memberDepositLogs = new HashSet<>();

    /**
     * 优惠码
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<CouponCode> couponCodes = new HashSet<>();

    /**
     * 收货地址
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("isDefault desc, createdDate desc")
    private Set<Receiver> receivers = new HashSet<>();

    /**
     * 评论
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdDate desc")
    private Set<Review> reviews = new HashSet<>();

    /**
     * 咨询
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdDate desc")
    private Set<Consultation> consultations = new HashSet<>();

    /**
     * 商品收藏
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ProductFavorite> productFavorites = new HashSet<>();

    /**
     * 店铺收藏
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<StoreFavorite> storeFavorites = new HashSet<>();

    /**
     * 到货通知
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ProductNotify> productNotifies = new HashSet<>();

    /**
     * 积分记录
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<PointLog> pointLogs = new HashSet<>();

    /**
     * 售后
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Aftersales> aftersales = new HashSet<>();

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
     * @param username 用户名
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
     * @param password 密码
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
     * @param encodedPassword 加密密码
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
     * @param email E-mail
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
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取积分
     *
     * @return 积分
     */
    public Long getPoint() {
        return point;
    }

    /**
     * 设置积分
     *
     * @param point 积分
     */
    public void setPoint(Long point) {
        this.point = point;
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
     * @param balance 余额
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
     * @param frozenAmount 冻结金额
     */
    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    /**
     * 获取消费金额
     *
     * @return 消费金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置消费金额
     *
     * @param amount 消费金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public Member.Gender getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Member.Gender gender) {
        this.gender = gender;
    }

    /**
     * 获取出生日期
     *
     * @return 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 获取地址
     *
     * @return 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮编
     *
     * @return 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取会员注册项值0
     *
     * @return 会员注册项值0
     */
    public String getAttributeValue0() {
        return attributeValue0;
    }

    /**
     * 设置会员注册项值0
     *
     * @param attributeValue0 会员注册项值0
     */
    public void setAttributeValue0(String attributeValue0) {
        this.attributeValue0 = attributeValue0;
    }

    /**
     * 获取会员注册项值1
     *
     * @return 会员注册项值1
     */
    public String getAttributeValue1() {
        return attributeValue1;
    }

    /**
     * 设置会员注册项值1
     *
     * @param attributeValue1 会员注册项值1
     */
    public void setAttributeValue1(String attributeValue1) {
        this.attributeValue1 = attributeValue1;
    }

    /**
     * 获取会员注册项值2
     *
     * @return 会员注册项值2
     */
    public String getAttributeValue2() {
        return attributeValue2;
    }

    /**
     * 设置会员注册项值2
     *
     * @param attributeValue2 会员注册项值2
     */
    public void setAttributeValue2(String attributeValue2) {
        this.attributeValue2 = attributeValue2;
    }

    /**
     * 获取会员注册项值3
     *
     * @return 会员注册项值3
     */
    public String getAttributeValue3() {
        return attributeValue3;
    }

    /**
     * 设置会员注册项值3
     *
     * @param attributeValue3 会员注册项值3
     */
    public void setAttributeValue3(String attributeValue3) {
        this.attributeValue3 = attributeValue3;
    }

    /**
     * 获取会员注册项值4
     *
     * @return 会员注册项值4
     */
    public String getAttributeValue4() {
        return attributeValue4;
    }

    /**
     * 设置会员注册项值4
     *
     * @param attributeValue4 会员注册项值4
     */
    public void setAttributeValue4(String attributeValue4) {
        this.attributeValue4 = attributeValue4;
    }

    /**
     * 获取会员注册项值5
     *
     * @return 会员注册项值5
     */
    public String getAttributeValue5() {
        return attributeValue5;
    }

    /**
     * 设置会员注册项值5
     *
     * @param attributeValue5 会员注册项值5
     */
    public void setAttributeValue5(String attributeValue5) {
        this.attributeValue5 = attributeValue5;
    }

    /**
     * 获取会员注册项值6
     *
     * @return 会员注册项值6
     */
    public String getAttributeValue6() {
        return attributeValue6;
    }

    /**
     * 设置会员注册项值6
     *
     * @param attributeValue6 会员注册项值6
     */
    public void setAttributeValue6(String attributeValue6) {
        this.attributeValue6 = attributeValue6;
    }

    /**
     * 获取会员注册项值7
     *
     * @return 会员注册项值7
     */
    public String getAttributeValue7() {
        return attributeValue7;
    }

    /**
     * 设置会员注册项值7
     *
     * @param attributeValue7 会员注册项值7
     */
    public void setAttributeValue7(String attributeValue7) {
        this.attributeValue7 = attributeValue7;
    }

    /**
     * 获取会员注册项值8
     *
     * @return 会员注册项值8
     */
    public String getAttributeValue8() {
        return attributeValue8;
    }

    /**
     * 设置会员注册项值8
     *
     * @param attributeValue8 会员注册项值8
     */
    public void setAttributeValue8(String attributeValue8) {
        this.attributeValue8 = attributeValue8;
    }

    /**
     * 获取会员注册项值9
     *
     * @return 会员注册项值9
     */
    public String getAttributeValue9() {
        return attributeValue9;
    }

    /**
     * 设置会员注册项值9
     *
     * @param attributeValue9 会员注册项值9
     */
    public void setAttributeValue9(String attributeValue9) {
        this.attributeValue9 = attributeValue9;
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
     * @param safeKey 安全密匙
     */
    public void setSafeKey(SafeKey safeKey) {
        this.safeKey = safeKey;
    }

    /**
     * 获取地区
     *
     * @return 地区
     */
    public Area getArea() {
        return area;
    }

    /**
     * 设置地区
     *
     * @param area 地区
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * 获取会员等级
     *
     * @return 会员等级
     */
    public MemberRank getMemberRank() {
        return memberRank;
    }

    /**
     * 设置会员等级
     *
     * @param memberRank 会员等级
     */
    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    /**
     * 获取分销员
     *
     * @return 分销员
     */
    public Distributor getDistributor() {
        return distributor;
    }

    /**
     * 设置分销员
     *
     * @param distributor 分销员
     */
    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    /**
     * 获取购物车
     *
     * @return 购物车
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * 设置购物车
     *
     * @param cart 购物车
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * 获取订单
     *
     * @return 订单
     */
    public Set<Order> getOrders() {
        return orders;
    }

    /**
     * 设置订单
     *
     * @param orders 订单
     */
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    /**
     * 获取会员预存款记录
     *
     * @return 会员预存款记录
     */
    public Set<MemberDepositLog> getMemberDepositLogs() {
        return memberDepositLogs;
    }

    /**
     * 设置会员预存款记录
     *
     * @param memberDepositLogs 会员预存款记录
     */
    public void setMemberDepositLogs(Set<MemberDepositLog> memberDepositLogs) {
        this.memberDepositLogs = memberDepositLogs;
    }

    /**
     * 获取优惠码
     *
     * @return 优惠码
     */
    public Set<CouponCode> getCouponCodes() {
        return couponCodes;
    }

    /**
     * 设置优惠码
     *
     * @param couponCodes 优惠码
     */
    public void setCouponCodes(Set<CouponCode> couponCodes) {
        this.couponCodes = couponCodes;
    }

    /**
     * 获取收货地址
     *
     * @return 收货地址
     */
    public Set<Receiver> getReceivers() {
        return receivers;
    }

    /**
     * 设置收货地址
     *
     * @param receivers 收货地址
     */
    public void setReceivers(Set<Receiver> receivers) {
        this.receivers = receivers;
    }

    /**
     * 获取评论
     *
     * @return 评论
     */
    public Set<Review> getReviews() {
        return reviews;
    }

    /**
     * 设置评论
     *
     * @param reviews 评论
     */
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * 获取咨询
     *
     * @return 咨询
     */
    public Set<Consultation> getConsultations() {
        return consultations;
    }

    /**
     * 设置咨询
     *
     * @param consultations 咨询
     */
    public void setConsultations(Set<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * 获取商品收藏
     *
     * @return 商品收藏
     */
    public Set<ProductFavorite> getProductFavorites() {
        return productFavorites;
    }

    /**
     * 设置商品收藏
     *
     * @param productFavorites 商品收藏
     */
    public void setProductFavorites(Set<ProductFavorite> productFavorites) {
        this.productFavorites = productFavorites;
    }

    /**
     * 获取店铺收藏
     *
     * @return 店铺收藏
     */
    public Set<StoreFavorite> getStoreFavorites() {
        return storeFavorites;
    }

    /**
     * 设置店铺收藏
     *
     * @param storeFavorites 店铺收藏
     */
    public void setStoreFavorites(Set<StoreFavorite> storeFavorites) {
        this.storeFavorites = storeFavorites;
    }

    /**
     * 获取到货通知
     *
     * @return 到货通知
     */
    public Set<ProductNotify> getProductNotifies() {
        return productNotifies;
    }

    /**
     * 设置到货通知
     *
     * @param productNotifies 到货通知
     */
    public void setProductNotifies(Set<ProductNotify> productNotifies) {
        this.productNotifies = productNotifies;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    /**
     * 获取积分记录
     *
     * @return 积分记录
     */
    public Set<PointLog> getPointLogs() {
        return pointLogs;
    }

    /**
     * 设置积分记录
     *
     * @param pointLogs 积分记录
     */
    public void setPointLogs(Set<PointLog> pointLogs) {
        this.pointLogs = pointLogs;
    }

    /**
     * 获取售后
     *
     * @return 售后
     */
    public Set<Aftersales> getAftersales() {
        return aftersales;
    }

    /**
     * 设置售后
     *
     * @param aftersales 售后
     */
    public void setAftersales(Set<Aftersales> aftersales) {
        this.aftersales = aftersales;
    }

    /**
     * 获取会员注册项值
     *
     * @param memberAttribute 会员注册项
     * @return 会员注册项值
     */
    @Transient
    public Object getAttributeValue(MemberAttribute memberAttribute) {
        if (memberAttribute == null || memberAttribute.getType() == null) {
            return null;
        }
        switch (memberAttribute.getType()) {
            case NAME:
                return getName();
            case GENDER:
                return getGender();
            case BIRTH:
                return getBirth();
            case AREA:
                return getArea();
            case ADDRESS:
                return getAddress();
            case ZIP_CODE:
                return getZipCode();
            case PHONE:
                return getPhone();
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
            case IMAGE:
            case SELECT:
                if (memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        return PropertyUtils.getProperty(this, propertyName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHECKBOX:
                if (memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        String propertyValue = String.valueOf(PropertyUtils.getProperty(this, propertyName));
                        if (StringUtils.isNotEmpty(propertyValue)) {
                            return JsonUtils.toObject(propertyValue, List.class);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case BANK_ADDRESS:{
                return getBankAddress();
            }
        }
        return null;
    }


    /**
     * @return licenseNumber
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * @param licenseNumber 要设置的 licenseNumber
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * @return licenseImage
     */
    public String getLicenseImage() {
        return licenseImage;
    }

    /**
     * @param licenseImage 要设置的 licenseImage
     */
    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    /**
     * @return legalPerson
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * @param legalPerson 要设置的 legalPerson
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * @return idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard 要设置的 idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return idCardImage
     */
    public String getIdCardImage() {
        return idCardImage;
    }

    /**
     * @param idCardImage 要设置的 idCardImage
     */
    public void setIdCardImage(String idCardImage) {
        this.idCardImage = idCardImage;
    }

    /**
     * @return organizationCode
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * @param organizationCode 要设置的 organizationCode
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * @return organizationImage
     */
    public String getOrganizationImage() {
        return organizationImage;
    }

    /**
     * @param organizationImage 要设置的 organizationImage
     */
    public void setOrganizationImage(String organizationImage) {
        this.organizationImage = organizationImage;
    }

    /**
     * @return identificationNumber
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * @param identificationNumber 要设置的 identificationNumber
     */
    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    /**
     * @return taxImage
     */
    public String getTaxImage() {
        return taxImage;
    }

    /**
     * @param taxImage 要设置的 taxImage
     */
    public void setTaxImage(String taxImage) {
        this.taxImage = taxImage;
    }

    /**
     * @return bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName 要设置的 bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * @param bankAccount 要设置的 bankAccount
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 设置会员注册项值
     *
     * @param memberAttribute      会员注册项
     * @param memberAttributeValue 会员注册项值
     */
    @Transient
    public void setAttributeValue(MemberAttribute memberAttribute, Object memberAttributeValue) {
        if (memberAttribute == null || memberAttribute.getType() == null) {
            return;
        }
        switch (memberAttribute.getType()) {
            case LICENSE_NUMBER:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setLicenseNumber((String) memberAttributeValue);
                }
                break;
            case LICENSE_IMAGE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setLicenseImage((String) memberAttributeValue);
                }
                break;
            case LEGAL_PERSON:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setLegalPerson((String) memberAttributeValue);
                }
                break;
            case ID_CARD:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setIdCard((String) memberAttributeValue);
                }
                break;
            case ID_CARD_IMAGE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setIdCardImage((String) memberAttributeValue);
                }
                break;
            case ORGANIZATION_CODE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setOrganizationCode((String) memberAttributeValue);
                }
                break;
            case ORGANIZATION_IMAGE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setOrganizationImage((String) memberAttributeValue);
                }
                break;
            case IDENTIFICATION_NUMBER:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setIdentificationNumber((String) memberAttributeValue);
                }
                break;
            case TAX_IMAGE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setTaxImage((String) memberAttributeValue);
                }
                break;
            case BANK_NAME:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setBankName((String) memberAttributeValue);
                }
                break;
            case BANK_ACCOUNT:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setBankAccount((String) memberAttributeValue);
                }
                break;
            case BANK_ADDRESS:{
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setBankAddress((String) memberAttributeValue);
                }
                break;
            }
            case NAME:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setName((String) memberAttributeValue);
                }
                break;
            case GENDER:
                if (memberAttributeValue instanceof Member.Gender || memberAttributeValue == null) {
                    setGender((Member.Gender) memberAttributeValue);
                }
                break;
            case BIRTH:
                if (memberAttributeValue instanceof Date || memberAttributeValue == null) {
                    setBirth((Date) memberAttributeValue);
                }
                break;
            case AREA:
                if (memberAttributeValue instanceof Area || memberAttributeValue == null) {
                    setArea((Area) memberAttributeValue);
                }
                break;
            case ADDRESS:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setAddress((String) memberAttributeValue);
                }
                break;
            case ZIP_CODE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setZipCode((String) memberAttributeValue);
                }
                break;
            case PHONE:
                if (memberAttributeValue instanceof String || memberAttributeValue == null) {
                    setPhone((String) memberAttributeValue);
                }
                break;
            case TEXT:
            case SELECT:
                if ((memberAttributeValue instanceof String || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        PropertyUtils.setProperty(this, propertyName, memberAttributeValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHECKBOX:
                if ((memberAttributeValue instanceof Collection || memberAttributeValue == null) && memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        PropertyUtils.setProperty(this, propertyName, memberAttributeValue != null ? JsonUtils.toJson(memberAttributeValue) : null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 移除所有会员注册项值
     */
    @Transient
    public void removeAttributeValue() {
        setName(null);
        setGender(null);
        setBirth(null);
        setArea(null);
        setAddress(null);
        setZipCode(null);
        setPhone(null);
        for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
            String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
            try {
                PropertyUtils.setProperty(this, propertyName, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
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

    /**
     * 判断是否为分销员
     *
     * @return 是否为分销员
     */
    @Transient
    public boolean getIsDistributor() {
        return getDistributor() != null;
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
        return getEncodedPassword();
    }

    @Override
    @Transient
    public boolean isValidCredentials(Object credentials) {
        return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword()) && getIsAudit().equals(CheckStatus.SUCCESS);
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

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
}