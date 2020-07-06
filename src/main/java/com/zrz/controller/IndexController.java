package com.zrz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zrz.entity.SysUserInfoPO;
import com.zrz.service.SysUserInfoService;
import com.zrz.util.Constans;
import com.zrz.util.CookieUtil;
import com.zrz.util.MemcachedUtils;
import com.zrz.util.ToolClass;
import com.zrz.util.UUIDUtil;
 
@Controller
@RequestMapping(value="")
public class IndexController {
 
	@Autowired
	private SysUserInfoService sysUserInfoService;
	
    @RequestMapping(value={"","/"})
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
    	
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/index/index");
        
        return mav;
    }
    
    @RequestMapping(value={"","/main"})
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response){
    	
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/index/main");
        
        return mav;
    }
    
    @RequestMapping(value={"/login"})
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("/home/login");
    	return mav;
    }
    
    @RequestMapping(value={"/register"})
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("/home/register");
    	return mav;
    }
    
    
    @RequestMapping(value={"/loginCommit"} , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> loginCommit(HttpServletRequest request, HttpServletResponse response){
    	Map<String, String> map = new HashMap<String, String>();
    	//验证账号密码
    	String account = request.getParameter("account");
    	String password = request.getParameter("password");
    	if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
    		map.put("flag", Constans.ERROR);
    		map.put("message", "账号或密码不能为空！");
    		return map;
    	}
    	SysUserInfoPO sysUserInfoPO = sysUserInfoService.getByAccount(account); 
    	if(sysUserInfoPO==null){
    		map.put("flag", Constans.ERROR);
    		map.put("message", "账号不存在！");
    		return map;
    	}else{
    		if(!password.equals(sysUserInfoPO.getPassword())){
    			map.put("flag", Constans.ERROR);
        		map.put("message", "账号或密码输入错误！");
        		return map;
    		}
    	}
    	
    	//生成cookie-tookenId
    	String tookenId = UUIDUtil.uuidRandom();
    	CookieUtil.addCookie(response, Constans.TOKEN_ID, tookenId, Integer.MAX_VALUE);
    	
    	//如果上次登录日期非当天，则送2银
    	String lastLogindate = sysUserInfoPO.getLastLogin().substring(0, 10);
    	if(!ToolClass.getDate().equals(lastLogindate)){
    		sysUserInfoPO.setGold(sysUserInfoPO.getGold()+ 200);
    	}
    	//记录登录时间
    	sysUserInfoPO.setLastLogin(ToolClass.getTime());
    	
    	sysUserInfoService.updateSelective(sysUserInfoPO);
    	
    	map.put("flag", Constans.SUCCESS);
    	map.put("message", "登陆成功！");
    	
    	//去除敏感信息
    	sysUserInfoPO.setAccount("");
    	sysUserInfoPO.setPassword("");
    	//存储memcached
    	MemcachedUtils.saveUserInfoPO(tookenId, sysUserInfoPO);
    	MemcachedUtils.set(sysUserInfoPO.getId(), sysUserInfoPO ,new Date(0));
    	
    	return map;
    }
    
    
	@RequestMapping(value={"/registerCommit"} , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> registerCommit(HttpServletRequest request, HttpServletResponse response){
    	Map<String, String> map = new HashMap<String, String>();
    	//验证账号密码名称
    	String account = request.getParameter("account");
    	String password = request.getParameter("password");
    	String name = request.getParameter("name");
    	if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(name)){
    		map.put("flag", Constans.ERROR);
    		map.put("message", "账号密码或者名称不能为空！");
    		return map;
    	}
    	//验证是否有重复
    	SysUserInfoPO sysUserInfoPO = sysUserInfoService.getByAccount(account); 
    	if(sysUserInfoPO!=null){
    		map.put("flag", Constans.ERROR);
    		map.put("message", "账号或已存在！");
    		return map;
    	}
    	//注册成功
    	sysUserInfoPO = new SysUserInfoPO();//清空
    	sysUserInfoPO.setAccount(account);
    	String nowTime = ToolClass.getTime();
    	sysUserInfoPO.setCreateTime(nowTime);
    	sysUserInfoPO.setGold(200);
    	sysUserInfoPO.setId(UUIDUtil.uuidRandom());
    	sysUserInfoPO.setLastLogin(nowTime);
    	sysUserInfoPO.setLastTime(nowTime);
    	sysUserInfoPO.setLevel(1);
    	sysUserInfoPO.setName(name);
    	sysUserInfoPO.setOptTimes(1);
    	sysUserInfoPO.setPassword(password);
    	
    	int rs = sysUserInfoService.save(sysUserInfoPO);
    	if(rs>0){
	    	map.put("flag", Constans.SUCCESS);
			map.put("message", "注册成功！");
    	}else{
    		map.put("flag", Constans.ERROR);
			map.put("message", "注册失败！");
    	}
    	
    	//生成cookie-tookenId
    	String tookenId = UUIDUtil.uuidRandom();
    	CookieUtil.addCookie(response, Constans.TOKEN_ID, tookenId, Integer.MAX_VALUE);
    	//存储memcached
    	MemcachedUtils.saveUserInfoPO(tookenId, sysUserInfoPO);
    
    	return map;
    }
    
	@RequestMapping(value={"/getUserInfoPO"} , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserInfoPO(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 
		SysUserInfoPO sysUserInfoPO = MemcachedUtils.getUserInfoPO(request);
		//清空重要信息
		sysUserInfoPO.setAccount("");
		sysUserInfoPO.setPassword("");
		//map put
		map.put("UserInfoPO", sysUserInfoPO);
		//message
		map.put("message", "");
		
		return map;
	}

}