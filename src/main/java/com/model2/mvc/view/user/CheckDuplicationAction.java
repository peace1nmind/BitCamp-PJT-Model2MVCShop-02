package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class CheckDuplicationAction extends Action{

	@Override
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		Debug.startAction("CheckDuplicationAction");
		
		String userId= Debug.getParamStr(request, "userId");
		
		UserService userService=new UserServiceImpl();
		boolean result=userService.checkDuplication(userId);
		
		Debug.printDataInAction("result", result);
		
		request.setAttribute("result",new Boolean(result) );
		request.setAttribute("userId", userId);
		
		Debug.endAction();
		
		return "forward:/user/checkDuplication.jsp";
	}
}