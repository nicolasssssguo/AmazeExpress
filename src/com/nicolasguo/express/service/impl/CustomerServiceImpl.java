package com.nicolasguo.express.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nicolasguo.express.condition.impl.CustomerCondition;
import com.nicolasguo.express.dao.IBaseEntityDao;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService<Customer, String> {

	@Resource(name = "customerDao")
	IBaseEntityDao<Customer> customerDao;

	@Override
	public Customer getCustomer(String id) {
		return customerDao.get(id);
	}

	@Override
	public Customer loadCustomer(String id) {
		return customerDao.load(id);
	}

	@Override
	public List<Customer> findCustomerByProperty(String propertyName, Object propertyValue) {
		return customerDao.findByProperty(propertyName, propertyValue);
	}

	@Override
	public List<Customer> findCustomerByCondition(CustomerCondition condition) {
		return customerDao.findByCondition(condition);
	}
	
	@Override
	public Page<Customer> findCustomerByCondition(CustomerCondition condition, Page<Customer> page) {
		return customerDao.findByCondition(condition, page);
	}

	@Override
	public void updateCustomer(Customer entity) {
		customerDao.update(entity);
	}

	@Override
	public void saveCustomer(Customer entity) {
		customerDao.save(entity);
	}

	@Override
	public void deleteCustomer(Customer entity) {
		customerDao.delete(entity);
	}

	@Override
	public void deleteCustomers(List<Customer> list) {
		customerDao.deleteEntitys(list);
	}
}
