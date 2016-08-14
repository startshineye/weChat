package com.founder.weixin.test;

import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;

/**
 * 移动用户分组
 * @author yxm
 *
 */
public class TestUpdateMemberGroup {
	public static void main(String[] args) {
		// 获取accessToken
		String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f",
				"19fe03289d3091a1e5476e35f59c620e").getAccessToken();
		// 移动用户分组
		AdvancedUtil.updateMemberGroup(accessToken, "12edasdas", 100);
	}
}
