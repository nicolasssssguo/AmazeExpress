package com.nicolasguo.express.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_customer")
public class Customer extends BaseEntityObject {

	private static final long serialVersionUID = 8647917616317987920L;

	private String name;

	private String phoneNumber;

	private String area;

	@Column(length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 11)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
