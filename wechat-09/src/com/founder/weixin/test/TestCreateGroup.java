package com.founder.weixin.test;

import com.founder.weixin.pojo.WeixinGroup;
import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;

/**
 * 测试创建分组
 * @author yxm
 */
public class TestCreateGroup {
	public static void main(String[] args) {
		// 获取accessToken
		String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f",
				"19fe03289d3091a1e5476e35f59c620e").getAccessToken();
		// 创建分组
		WeixinGroup group = AdvancedUtil.createGroup(accessToken, "金融部");

		System.out.println(String.format("分组:%s id: %d", group.getName(), group
				.getId()));
	}
}
