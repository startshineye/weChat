package com.founder.weixin.test;
import java.util.List;
import com.founder.weixin.pojo.WeixinGroup;
import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;
/**
 * 测试查询分组信息
 * @author yxm
 */
public class TestGetGroups {
	public static void main(String[] args) {
		// 获取accessToken
		String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f",
				"19fe03289d3091a1e5476e35f59c620e").getAccessToken();
		//System.out.println(accessToken);
		// 获取用户分组信息
		List<WeixinGroup> groupList = AdvancedUtil.getGroup(accessToken);
		
		System.out.println(groupList.toString());
		// 输出信息
		for (WeixinGroup group : groupList) {
			System.out.println(String.format("ID:%d 名称：%s 用户组：%d",group.getId(),group.getName(),group.getCount()));
		}
	}
}
