package com.nicolasguo.express.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_express")
public class Express extends BaseEntityObject {

	private Customer recipient;

	private Area dest;

	private int status;

	private Date arriveDate;

	private Date signTime;

	@ManyToOne
	public Customer getRecipient() {
		return recipient;
	}

	public void setRecipient(Customer recipient) {
		this.recipient = recipient;
	}

	@ManyToOne
	public Area getDest() {
		return dest;
	}

	public void setDest(Area dest) {
		this.dest = dest;
	}

	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

}
