package com.nicolasguo.express.service;

import java.io.Serializable;
import java.util.List;

import com.nicolasguo.express.condition.impl.CustomerCondition;
import com.nicolasguo.express.entity.Page;

public interface CustomerService<T, PK extends Serializable> {
	
	public T getCustomer(PK id);

	public T loadCustomer(PK id);

	public List<T> findCustomerByProperty(String propertyName, Object propertyValue);
	
	@SuppressWarnings("rawtypes")
	public List<T> findCustomerByCondition(CustomerCondition condition);
	
	@SuppressWarnings("rawtypes")
	public Page<T> findCustomerByCondition(CustomerCondition condition, Page<T> page);

	public void updateCustomer(T entity);

	public void saveCustomer(T entity);

	public void deleteCustomer(T entity);

	public void deleteCustomers(List<T> list);
	
}
