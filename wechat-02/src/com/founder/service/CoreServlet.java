package com.founder.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.founder.util.ValidationUtil;
/**
 * 核心请求处理类
 * @author yxm
 * @date 2016-8-4
 */
public class CoreServlet extends HttpServlet {
	/**
	 * 确认请求来自微信服务器（微信服务器（腾讯服务器）发送验证消息时候是用get请求。）
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		
		System.out.println("加密的签名字符串："+signature);
		System.out.println("时间戳："+timestamp);
		System.out.println("随机数："+nonce);
		System.out.println("随机字符串："+echostr);
		PrintWriter out = response.getWriter();
	    //通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败 
		if(ValidationUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		//关闭输出流
		out.close();
	}
	
	/**
	 * 处理来自微信服务器的请求消息（微信服务器（腾讯服务器）发送消息是用post请求。）
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      //设置来去编码格式
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//通过检验signature对请求进行校验，若校验成功则返回我们自己的方法
		if(ValidationUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
}