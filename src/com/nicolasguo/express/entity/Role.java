package com.nicolasguo.express.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.nicolasguo.express.common.ProjectConfig;

@Entity
@Table(name = "tb_role")
public class Role extends BaseEntityObject {

	private static final long serialVersionUID = 635469332843374297L;

	private String roleName;

	private String desciption;

	@Column(length = 255)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(length = 255)
	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	@Transient
	public String getAuthority() {
		return roleName;
	}
}
