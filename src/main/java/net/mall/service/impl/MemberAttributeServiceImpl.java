/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.CommonAttributes;
import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.AreaDao;
import net.mall.dao.MemberAttributeDao;
import net.mall.dao.MemberDao;
import net.mall.entity.Area;
import net.mall.entity.Member;
import net.mall.entity.MemberAttribute;
import net.mall.service.MemberAttributeService;

/**
 * Service - 会员注册项
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class MemberAttributeServiceImpl extends BaseServiceImpl<MemberAttribute, Long> implements MemberAttributeService {

	@Inject
	private MemberAttributeDao memberAttributeDao;
	@Inject
	private MemberDao memberDao;
	@Inject
	private AreaDao areaDao;

	@Override
	@Transactional(readOnly = true)
	public Integer findUnusedPropertyIndex() {
		return memberAttributeDao.findUnusedPropertyIndex();
	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders) {
		return memberAttributeDao.findList(isEnabled, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "memberAttribute", condition = "#useCache")
	public List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return memberAttributeDao.findList(isEnabled, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "memberAttribute", condition = "#useCache")
	public List<MemberAttribute> findList(Boolean isEnabled, boolean useCache) {
		return memberAttributeDao.findList(isEnabled, null, null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isValid(MemberAttribute memberAttribute, String[] values) {
		Assert.notNull(memberAttribute, "[Assertion failed] - memberAttribute is required; it must not be null");
		Assert.notNull(memberAttribute.getType(), "[Assertion failed] - memberAttribute type is required; it must not be null");

		String value = ArrayUtils.isNotEmpty(values) ? values[0].trim() : null;
		switch (memberAttribute.getType()) {
		case NAME:
		case ADDRESS:
		case ZIP_CODE:
		case PHONE:
		case TEXT:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(memberAttribute.getPattern()) && StringUtils.isNotEmpty(value) && !Pattern.compile(memberAttribute.getPattern()).matcher(value).matches()) {
				return false;
			}
			break;
		case GENDER:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(value)) {
				try {
					Member.Gender.valueOf(value);
				} catch (IllegalArgumentException e) {
					return false;
				}
			}
			break;
		case BIRTH:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (StringUtils.isNotEmpty(value)) {
				try {
					DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS);
				} catch (ParseException e) {
					return false;
				}
			}
			break;
		case AREA:
			Long id = NumberUtils.toLong(value, -1L);
			Area area = areaDao.find(id);
			if (memberAttribute.getIsRequired() && area == null) {
				return false;
			}
			break;
		case SELECT:
			if (memberAttribute.getIsRequired() && StringUtils.isEmpty(value)) {
				return false;
			}
			if (CollectionUtils.isEmpty(memberAttribute.getOptions())) {
				return false;
			}
			if (StringUtils.isNotEmpty(value) && !memberAttribute.getOptions().contains(value)) {
				return false;
			}
			break;
		case CHECKBOX:
			if (memberAttribute.getIsRequired() && ArrayUtils.isEmpty(values)) {
				return false;
			}
			if (CollectionUtils.isEmpty(memberAttribute.getOptions())) {
				return false;
			}
			if (ArrayUtils.isNotEmpty(values) && !memberAttribute.getOptions().containsAll(Arrays.asList(values))) {
				return false;
			}
			break;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Object toMemberAttributeValue(MemberAttribute memberAttribute, String[] values) {
		Assert.notNull(memberAttribute, "[Assertion failed] - memberAttribute is required; it must not be null");
		Assert.notNull(memberAttribute.getType(), "[Assertion failed] - memberAttribute type is required; it must not be null");
		if (ArrayUtils.isEmpty(values)) {
			return null;
		}
		String value = values[0].trim();
		switch (memberAttribute.getType()) {
		case NAME:
		case ADDRESS:
		case ZIP_CODE:
		case PHONE:
		case LICENSE_NUMBER:
		case LICENSE_IMAGE:
		case LEGAL_PERSON:
		case ID_CARD:
		case ID_CARD_IMAGE:
		case ORGANIZATION_CODE:
		case ORGANIZATION_IMAGE:
		case IDENTIFICATION_NUMBER:
		case TAX_IMAGE:
		case BANK_NAME:
		case BANK_ACCOUNT:
		case IMAGE:
		case TEXT:
			return StringUtils.isNotEmpty(value) ? value : null;
		case GENDER:
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return Member.Gender.valueOf(value);
			} catch (IllegalArgumentException e) {
				return null;
			}
		case BIRTH:
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			try {
				return DateUtils.parseDate(value, CommonAttributes.DATE_PATTERNS);
			} catch (ParseException e) {
				return null;
			}
		case AREA:
			Long id = NumberUtils.toLong(value, -1L);
			return areaDao.find(id);
		case SELECT:
			if (CollectionUtils.isNotEmpty(memberAttribute.getOptions()) && memberAttribute.getOptions().contains(value)) {
				return value;
			}
			break;
		case CHECKBOX:
			if (CollectionUtils.isNotEmpty(memberAttribute.getOptions()) && memberAttribute.getOptions().containsAll(Arrays.asList(values))) {
				return Arrays.asList(values);
			}
			break;
		}
		return null;
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute save(MemberAttribute memberAttribute) {
		Assert.notNull(memberAttribute, "[Assertion failed] - memberAttribute is required; it must not be null");

		Integer unusedPropertyIndex = memberAttributeDao.findUnusedPropertyIndex();
		Assert.notNull(unusedPropertyIndex, "[Assertion failed] - unusedPropertyIndex is required; it must not be null");

		memberAttribute.setPropertyIndex(unusedPropertyIndex);

		return super.save(memberAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute update(MemberAttribute memberAttribute) {
		return super.update(memberAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public MemberAttribute update(MemberAttribute memberAttribute, String... ignoreProperties) {
		return super.update(memberAttribute, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "memberAttribute", allEntries = true)
	public void delete(MemberAttribute memberAttribute) {
		if (memberAttribute != null) {
			memberDao.clearAttributeValue(memberAttribute);
		}

		super.delete(memberAttribute);
	}

}