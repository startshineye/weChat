package com.founder.weixin.test;

import com.founder.weixin.util.CommonUtil;

public class TestOAuthLink {

	/**
	 * 获取最终网页授权链接
	 * @param args
	 */
	public static void main(String[] args) {
		//拼接接口链接
		String oauthUrl = "http://99bc8d18.ngrok.io/wxkf/oauthServlet";
		String result = "";
		result = CommonUtil.urlEncodeUTF8(oauthUrl);
		System.out.println(result);
	}
}
