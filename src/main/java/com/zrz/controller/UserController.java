package com.zrz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zrz.entity.SysUserInfoPO;
import com.zrz.service.SysUserInfoService;
 
@Controller
@RequestMapping(value="/user")
public class UserController {
 
	@Autowired
	private SysUserInfoService sysUserInfoService;
	
	
	@RequestMapping(value = "/getUserInfoPO", method = RequestMethod.POST)
	@ResponseBody
	public SysUserInfoPO getUserInfoPO(HttpServletRequest request, HttpServletResponse response) {

		String userId = request.getParameter("userId");
		return sysUserInfoService.getById(userId);
	}
	
	@RequestMapping(value = "/getGold", method = RequestMethod.POST)
	@ResponseBody
	public String getGold(HttpServletRequest request, HttpServletResponse response) {

		String userId = request.getParameter("userId");
		return sysUserInfoService.getById(userId).getGold()+"";
	}

}