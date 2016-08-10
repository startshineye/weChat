package com.founder.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.founder.pojo.SNSUserInfo;
import com.founder.pojo.WeixinOauth2Token;
import com.founder.util.OAuthUtil;
/**
* 授权后的回调请求处理
* @author yxm
* @date 2016-08-10
 */
public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 6232099821294027448L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("gb2312");
		 response.setCharacterEncoding("gb2312");
		 
		 //获取 code（用户同意授权后）
		 String code = request.getParameter("code");
		 
		 //用户同意授权
		 if(!"authdeny".equals(code)){
			 //获取网页授权access_token
			 WeixinOauth2Token oauth2AccessToken = OAuthUtil.getOauth2AccessToken("appId", "appSecret", code);
		     //网页授权接口凭证
			 String accessToken = oauth2AccessToken.getAccessToken();
			 //用户标识
			 String openId = oauth2AccessToken.getOpenId();
			 
			 //根据accessToken和openId获取用户信息
			 SNSUserInfo snsUserInfo = OAuthUtil.getSNSUserInfo(accessToken, openId);
			 
			 //将snsUserInfo保存在request中用于显示到前端
			 request.setAttribute("snsUserInfo", snsUserInfo);
		 }
		 //用户不同意授权直接跳转到index.jsp页面
		 request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
	}
     
}
