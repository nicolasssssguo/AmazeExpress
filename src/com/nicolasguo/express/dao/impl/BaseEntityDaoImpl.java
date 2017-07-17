package com.nicolasguo.express.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.nicolasguo.express.condition.HibernateCondition;
import com.nicolasguo.express.dao.IBaseEntityDao;
import com.nicolasguo.express.entity.Page;

public class BaseEntityDaoImpl<T> implements IBaseEntityDao<T> {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;

	public BaseEntityDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public Session openSession(){
		return sessionFactory.openSession();
	}
	
	@Override
	public T get(String id) {
		return openSession().get(entityClass, id);
	}

	@Override
	public T load(String id) {
		return openSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		String queryString = "from " + this.entityClass.getSimpleName() + " t where t." + propertyName + " = :" + propertyName;
		return openSession().createQuery(queryString)
				.setParameter(propertyName, propertyValue)
				.list();
	}

	public void delete(T entity) {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		session.close();
	}

	@Override
	public void deleteEntitys(List<T> list) {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		list.forEach(session::delete);
		session.flush();
		tx.commit();
		session.close();
	}

	public void save(T entity) {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}

	public void update(T entity) {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		session.close();
	}
	
	public long count(){
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.getExecutableCriteria(openSession()).uniqueResult();
	}
	
	public long count(HibernateCondition condition){
		DetachedCriteria criteria = condition.getCriteria();
		criteria.setProjection(Projections.rowCount());
		return (long) criteria.getExecutableCriteria(openSession()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		String queryString = "from " + entityClass.getSimpleName();
		return openSession().createQuery(queryString)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findAll(Page<T> page) {
		String queryString = "from " + entityClass.getSimpleName() + " order by createTime desc";
		List<T> result = openSession().createQuery(queryString)
				.setFirstResult(page.getPageSize() * (page.getPageNo() - 1))
				.setMaxResults(page.getPageSize())
				.list();
		page.setTotalRecords(count());
		page.setList(result);
		return page;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findByCondition(HibernateCondition condition) {
		return condition.getCriteria()
				.getExecutableCriteria(openSession())
				.list();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<T> findByCondition(HibernateCondition condition, Page<T> page) {
		List<T> result = condition.getCriteria()
				.getExecutableCriteria(openSession())
				.setFirstResult(page.getPageSize() * (page.getPageNo() - 1))
				.setMaxResults(page.getPageSize())
				.list();
		page.setTotalRecords(count(condition));
		page.setList(result);
		return page;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List findByCriteria(DetachedCriteria criteria){
		return criteria.getExecutableCriteria(openSession())
				.list();
	}
}
