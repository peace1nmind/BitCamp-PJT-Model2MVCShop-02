package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class GetUserAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("GetUserAction");
		
		String userId= Debug.getParamStr(request, "userId");
		
		UserService userService=new UserServiceImpl();
		User user=userService.getUser(userId);
		
		Debug.printDataInAction("user", user);
		
		request.setAttribute("user", user);
		
		Debug.endAction();
		
		return "forward:/user/getUser.jsp";
	}
}