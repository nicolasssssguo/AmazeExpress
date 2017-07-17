package com.nicolasguo.express.condition.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.nicolasguo.express.condition.HibernateCondition;
import com.nicolasguo.express.entity.User;

public class UserCondition extends HibernateCondition<User> {

	private String username;

	private String password;

	@Override
	public void createCriteria(DetachedCriteria criteria) {
		if(username != null){
			criteria.add(Restrictions.eq("username", username));
		}
		if(password != null){
			criteria.add(Restrictions.eq("password", password));
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
