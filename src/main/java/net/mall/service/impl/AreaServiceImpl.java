/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.dao.AreaDao;
import net.mall.entity.Area;
import net.mall.service.AreaService;

/**
 * Service - 地区
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	@Inject
	private AreaDao areaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots() {
		return areaDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findRoots(Integer count) {
		return areaDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findParents(Area area, boolean recursive, Integer count) {
		return areaDao.findParents(area, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findChildren(Area area, boolean recursive, Integer count) {
		return areaDao.findChildren(area, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area save(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		return super.save(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area update(Area area) {
		Assert.notNull(area, "[Assertion failed] - area is required; it must not be null");

		setValue(area);
		for (Area children : areaDao.findChildren(area, true, null)) {
			setValue(children);
		}
		return super.update(area);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public Area update(Area area, String... ignoreProperties) {
		return super.update(area, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "areaPage", allEntries = true)
	public void delete(Area area) {
		super.delete(area);
	}

	/**
	 * 设置值
	 * 
	 * @param area
	 *            地区
	 */
	private void setValue(Area area) {
		if (area == null) {
			return;
		}
		Area parent = area.getParent();
		if (parent != null) {
			area.setFullName(parent.getFullName() + area.getName());
			area.setTreePath(parent.getTreePath() + parent.getId() + Area.TREE_PATH_SEPARATOR);
		} else {
			area.setFullName(area.getName());
			area.setTreePath(Area.TREE_PATH_SEPARATOR);
		}
		area.setGrade(area.getParentIds().length);
	}

}