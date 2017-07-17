package com.nicolasguo.express.condition.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.nicolasguo.express.condition.HibernateCondition;
import com.nicolasguo.express.entity.Customer;

public class CustomerCondition extends HibernateCondition<Customer> {

	private String name;

	private String phoneNumber;

	private String keynumber;

	private List<String> ids;

	@Override
	public void createCriteria(DetachedCriteria criteria) {
		if(StringUtils.isNoneEmpty(name)){
			criteria.add(Restrictions.eq("name", name));
		}
		if(phoneNumber != null){
			criteria.add(Restrictions.like("phoneNumber", phoneNumber, MatchMode.ANYWHERE));
		}
		if (keynumber != null) {
			criteria.add(Restrictions.like("phoneNumber", keynumber, MatchMode.END));
		}
		if (ids != null) {
			criteria.add(Restrictions.in("id", ids));
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setKeynumber(String keynumber) {
		this.keynumber = keynumber;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
