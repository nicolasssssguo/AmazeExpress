package com.nicolasguo.express.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nicolasguo.express.condition.impl.UserCondition;
import com.nicolasguo.express.dao.IBaseEntityDao;
import com.nicolasguo.express.entity.User;
import com.nicolasguo.express.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService<User, String> {

	@Resource(name = "userDao")
	IBaseEntityDao<User> userDao;
	
	@Override
	public User getUser(String id) {
		return userDao.get(id);
	}

	@Override
	public User loadUser(String id) {
		return userDao.load(id);
	}

	@Override
	public void updateUser(User entity) {
		userDao.update(entity);
	}

	@Override
	public void saveUser(User entity) {
		userDao.save(entity);
	}

	@Override
	public void deleteUser(User entity) {
		userDao.delete(entity);
	}

	@Override
	public void deleteUsers(List<User> list) {
		userDao.deleteEntitys(list);
	}

	@Override
	public List<User> findUserByProperty(String propertyName, String propertyValue) {
		return userDao.findByProperty(propertyName, propertyValue);
	}

	@Override
	public List<User> findUserByCondition(UserCondition condition) {
		return userDao.findByCondition(condition);
	}

}
