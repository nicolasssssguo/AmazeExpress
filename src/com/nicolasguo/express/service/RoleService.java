package com.nicolasguo.express.service;

import java.io.Serializable;
import java.util.List;

public interface RoleService<T, PK extends Serializable> {
	
	public T getRole(PK id);
	
	public List<T> findRoleByProperty(String propertyName, String propertyValue);

	public void updateRole(T entity);

	public void saveRole(T entity);

	public void deleteRole(T entity);
}
