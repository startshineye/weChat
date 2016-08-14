package com.founder.weixin.test;

import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;

/**
 * 测试修改分组
 * @author yxm
 *
 */
public class TestUpdateGroup {
	public static void main(String[] args) {
		// 获取accessToken
		String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f",
				"19fe03289d3091a1e5476e35f59c620e").getAccessToken();
		//修改
		AdvancedUtil.updateGroup(accessToken, 101, "人事");
		 
	}	
}
