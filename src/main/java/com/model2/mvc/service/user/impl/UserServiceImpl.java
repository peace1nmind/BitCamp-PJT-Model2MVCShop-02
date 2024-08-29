package com.model2.mvc.service.user.impl;

import java.util.Map;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDao;
import com.model2.mvc.service.domain.User;


public class UserServiceImpl implements UserService{
	
	//Field
	private UserDao userDao;
	
	//Constructor
	public UserServiceImpl() {
		Debug.setServiceName("UserServiceImpl");
		userDao=new UserDao();
	}

	//Method
	// 회원가입
	public void addUser(User user) throws Exception {
		userDao.insertUser(user);
	}
	
	// 로그인
	public User loginUser(User user) throws Exception {
		User dbUser=userDao.findUser(user.getUserId());

		if(! dbUser.getPassword().equals(user.getPassword())){
			throw new Exception("로그인에 실패했습니다.");
		}
		
		return dbUser;
	}
	
	// 회원정보
	public User getUser(String userId) throws Exception {
		return userDao.findUser(userId);
	}
	
	// 회원 리스트
	public Map<String,Object> getUserList(Search search) throws Exception {
		return userDao.getUserList(search);
	}
	
	// 회원정보 수정
	public void updateUser(User user) throws Exception {
		userDao.updateUser(user);
	}
	
	// 아이디 중복검사
	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		User user=userDao.findUser(userId);
		if(user != null) {
			result=false;
		}
		return result;
	}
	
}