package com.nicolasguo.express.condition;

import java.lang.reflect.ParameterizedType;

import org.hibernate.criterion.DetachedCriteria;

public abstract class HibernateCondition<T> {

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public HibernateCondition() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public DetachedCriteria getCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		createCriteria(criteria);
		return criteria;
	}

	public abstract void createCriteria(DetachedCriteria criteria);
}
