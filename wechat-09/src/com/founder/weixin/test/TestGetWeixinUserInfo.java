package com.founder.weixin.test;

import com.founder.weixin.pojo.WeixinUserInfo;
import com.founder.weixin.util.AdvancedUtil;
import com.founder.weixin.util.CommonUtil;

/**
 * 测试获取用户基本信息
 * @author yxm
 *
 */
public class TestGetWeixinUserInfo {
    public static void main(String[] args){
		//获取访问接口凭证
    	String accessToken = CommonUtil.getToken("wx73d9bb98d3a0175f", "19fe03289d3091a1e5476e35f59c620e").getAccessToken();
    	//获取openId
    	 WeixinUserInfo userInfo = AdvancedUtil.getUserInfo(accessToken, "oFoE3wpTd5izHeDCUeNYDqz9YzMI");
    	 System.out.println(userInfo.toString());
	}
}
