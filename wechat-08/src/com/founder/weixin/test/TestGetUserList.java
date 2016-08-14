package com.founder.weixin.test;

import com.founder.weixin.pojo.WeixinUserList;
import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;

public class TestGetUserList{

	/**获取关注者用户列表
	 * @param args
	 */
	public static void main(String[] args) {
		//获取accesssToken
		String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f", "19fe03289d3091a1e5476e35f59c620e").getAccessToken();
		System.out.println("accessToken为："+accessToken);
		//根据accessToken获取关注者用户列表
		WeixinUserList userList = AdvancedUtil.getUserList(accessToken, "");
		System.out.println(userList.toString());
	}
}
