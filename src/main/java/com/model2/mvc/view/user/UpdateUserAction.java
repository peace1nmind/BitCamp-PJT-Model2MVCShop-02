package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class UpdateUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		Debug.startAction("UpdateUserAction");
		
		String userId= Debug.getParamStr(request, "userId");
		
		User user=new User();
		user.setUserId(userId);
		user.setUserName(Debug.getParamStr(request, "userName"));
		user.setAddr(Debug.getParamStr(request, "addr"));
		user.setPhone(Debug.getParamStr(request, "phone"));
		user.setEmail(Debug.getParamStr(request, "email"));
		
		UserService userService=new UserServiceImpl();
		userService.updateUser(user);
		
		HttpSession session=request.getSession();
		String sessionId=((User)session.getAttribute("user")).getUserId();
		
		if(sessionId.equals(userId)){
			Debug.printDataInAction("user", user);
			session.setAttribute("user", user);
		}
		
		Debug.endAction();
		
		return "redirect:/getUser.do?userId="+userId;
	}
}