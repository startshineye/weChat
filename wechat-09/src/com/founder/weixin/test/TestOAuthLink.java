package com.founder.weixin.test;

import com.founder.weixin.util.CommonUtil;

public class TestOAuthLink {

	/**
	 * 获取最终网页授权链接
	 * @param args
	 */
	public static void main(String[] args) {
		//拼接接口链接
		String oauthUrl = "http://f8f6d81e.ngrok.io/wxkf/coreServlet";
		String result = "";
		result = CommonUtil.urlEncodeUTF8(oauthUrl);
		System.out.println(result);
	}
}
