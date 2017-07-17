package com.nicolasguo.express.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nicolasguo.express.condition.impl.AreaCondition;
import com.nicolasguo.express.dao.IBaseEntityDao;
import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.service.AreaService;

@Service("areaService")
public class AreaServiceImpl implements AreaService<Area, String> {

	@Resource(name = "areaDao")
	IBaseEntityDao<Area> areaDao;

	@Override
	public Area getArea(String id) {
		return areaDao.get(id);
	}

	@Override
	public Area loadArea(String id) {
		return areaDao.load(id);
	}

	@Override
	public List<Area> findAll() {
		return areaDao.findAll();
	}
	
	@Override
	public List<Area> findAreaByProperty(String propertyName, Object propertyValue) {
		return areaDao.findByProperty(propertyName, propertyValue);
	}

	@Override
	public List<Area> findAreaByCondition(AreaCondition condition) {
		return areaDao.findByCondition(condition);
	}

	@Override
	public void updateArea(Area entity) {
		areaDao.update(entity);
	}

	@Override
	public void saveArea(Area entity) {
		areaDao.save(entity);
	}

	@Override
	public void deleteArea(Area entity) {
		areaDao.delete(entity);
	}

	@Override
	public void deleteAreas(List<Area> list) {
		areaDao.deleteEntitys(list);
	}
}
