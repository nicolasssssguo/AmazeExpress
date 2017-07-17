package com.nicolasguo.express.service;

import java.io.Serializable;
import java.util.List;

import com.nicolasguo.express.condition.impl.AreaCondition;

public interface AreaService<T, PK extends Serializable> {

	public T getArea(PK id);

	public T loadArea(PK id);
	
	public List<T> findAll();

	public List<T> findAreaByProperty(String propertyName, Object propertyValue);

	@SuppressWarnings("rawtypes")
	public List<T> findAreaByCondition(AreaCondition condition);

	public void updateArea(T entity);

	public void saveArea(T entity);

	public void deleteArea(T entity);

	public void deleteAreas(List<T> list);
}
