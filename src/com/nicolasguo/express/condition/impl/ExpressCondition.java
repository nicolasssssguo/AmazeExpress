package com.nicolasguo.express.condition.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nicolasguo.express.condition.HibernateCondition;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;

public class ExpressCondition extends HibernateCondition<Express> {

	private Date startDate;

	private Date endDate;

	private Customer recipient;

	private Integer status;

	private List<String> ids;

	private String endingNumber;

	private String area;

	@Override
	public void createCriteria(DetachedCriteria criteria) {
		if (startDate != null) {
			criteria.add(Restrictions.ge("arriveDate", startDate));
		}
		if (endDate != null) {
			criteria.add(Restrictions.le("arriveDate", endDate));
		}
		if (recipient != null) {
			criteria.add(Restrictions.eq("recipient", recipient));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (ids != null) {
			criteria.add(Restrictions.in("id", ids));
		}
		if (endingNumber != null) {
			criteria.createAlias("recipient", "recipient");
			criteria.add(Restrictions.like("recipient.phoneNumber", endingNumber, MatchMode.END));
		}
		if (StringUtils.isNotEmpty(area)) {
			criteria.createAlias("dest", "dest");
			criteria.add(Restrictions.eq("dest.code", area));
		}
		criteria.addOrder(Order.asc("status"));
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setRecipient(Customer recipient) {
		this.recipient = recipient;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setEndingNumber(String endingNumber) {
		this.endingNumber = endingNumber;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
