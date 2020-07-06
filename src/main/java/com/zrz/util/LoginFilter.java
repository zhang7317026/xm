package com.zrz.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zrz.entity.SysUserInfoPO;


public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(true);
		// 获取URL
		String url = request.getRequestURI();
		// 跳转到登陆URL
		String loginUrl = "/xm/login";
		// 获取IP
		String IP = ToolClass.getIp(request);
		// IP放在session中
		session.setAttribute("IP", IP);
		
		boolean isNeed = false;
		for(String urlEx : Constans.URL_EXIT){
			if(url.toLowerCase().endsWith(urlEx)){
				isNeed = false;
				break;
			}
		}
		//如果需要登录验证
		if(isNeed){
			SysUserInfoPO sysUserInfoPO = MemcachedUtils.getUserInfoPO(request);
			if(sysUserInfoPO==null||"".equals(sysUserInfoPO.getId())){
				StringBuffer str = new StringBuffer();
				str.append("<script language='javascript'>");
				str.append("window.top.location.href='" + loginUrl+"';");
				str.append("</script>");
				
				response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
				try {
					PrintWriter writer = response.getWriter();
					writer.write(str.toString());
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				// 允许跳转
				chain.doFilter(request, response);
			}
			
		}else{
			// 允许跳转
			chain.doFilter(request, response);
		}

	}



	@Override
	public void destroy() {

	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}