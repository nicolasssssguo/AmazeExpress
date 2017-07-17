package com.nicolasguo.express.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nicolasguo.express.dao.IBaseEntityDao;
import com.nicolasguo.express.dao.impl.BaseEntityDaoImpl;
import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.User;

@Configuration
public class ProjectConfig {

	public static final String PREFIX = "express";

	public static final String NAME = PREFIX + ".";
	
	@Bean(name = "userDao")
	public IBaseEntityDao<User> generateUserDao(){
		return new BaseEntityDaoImpl<User>(User.class);
	}
	
	@Bean(name = "areaDao")
	public IBaseEntityDao<Area> generateAreaDao(){
		return new BaseEntityDaoImpl<Area>(Area.class);
	}
	
	@Bean(name = "expressDao")
	public IBaseEntityDao<Express> generateExpressDao(){
		return new BaseEntityDaoImpl<Express>(Express.class);
	}

	@Bean(name = "customerDao")
	public IBaseEntityDao<Customer> generateCustomerDao(){
		return new BaseEntityDaoImpl<Customer>(Customer.class);
	}
}
