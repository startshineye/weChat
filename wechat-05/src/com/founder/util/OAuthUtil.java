package com.founder.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.founder.pojo.SNSUserInfo;
import com.founder.pojo.WeixinOauth2Token;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * 网络授权工具类
 * @author yxm
 * @ date 2016-08-10
 */
public class OAuthUtil {
	private static Logger log = LoggerFactory.getLogger(OAuthUtil.class);
    /**
     * 获取网页授权凭证（access_token）
     * @param appId 公众号凭证
     * @param appSecret 公众号密匙
     * @param code
     * @return WeixinOauth2Token 
     */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,
			String appSecret, String code) {
		WeixinOauth2Token wot = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);

		// 获取网页授权凭证
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		System.out.println(jsonObj.toString());
		// 返回成功
		if (jsonObj != null) {
			try {
				wot = new WeixinOauth2Token();
				wot.setAccessToken(jsonObj.getString("access_token"));
				wot.setExpiresIn(jsonObj.getLong("expires_in"));
				wot.setOpenId(jsonObj.getString("openid"));
				wot.setRefreshToken(jsonObj.getString("refresh_token"));
				wot.setScope(jsonObj.getString("scope"));
			} catch (Exception e) {
				wot = null;
				int errorCode = jsonObj.getInt("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{}errmasg:{}",errorCode,errorMsg);
			}

		}
		return wot;
	}
	   /**
     * 刷新网页授权凭证（access_token）
     * @param appId 公众号凭证
     * @param refreshToken  
     * @param code
     * @return WeixinOauth2Token 
     */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId,
			String refreshToken, String code){
		WeixinOauth2Token wot = null;
		//拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		
		//刷新网页授权凭证
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		// 返回成功
		if (jsonObj != null) {
			try {
				wot = new WeixinOauth2Token();
				wot.setAccessToken(jsonObj.getString("access_token"));
				wot.setExpiresIn(jsonObj.getLong("expires_in"));
				wot.setOpenId(jsonObj.getString("openid"));
				wot.setRefreshToken(jsonObj.getString("refresh_token"));
				wot.setScope(jsonObj.getString("scope"));
			} catch (Exception e) {
				wot = null;
				int errorCode = jsonObj.getInt("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{}errmasg:{}",errorCode,errorMsg);
			}

		}
		return wot;
	}
	/**
	 * 通过accessToken和openId获取用户信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static SNSUserInfo getSNSUserInfo(String accessToken,String openId){
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID",
				openId);
		// 获取用户信息
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObj != null) {
			try {
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setCity(jsonObj.getString("city"));
				snsUserInfo.setCountry(jsonObj.getString("country"));
				snsUserInfo.setHeadimgurl(jsonObj.getString("headimgurl"));
				snsUserInfo.setNickname(jsonObj.getString("nickname"));
				snsUserInfo.setOpenId(jsonObj.getString("openid"));
				snsUserInfo.setProvince(jsonObj.getString("province"));
				snsUserInfo.setSex(jsonObj.getInt("sex"));
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObj
						.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
               snsUserInfo = null;
               int errorCode = jsonObj.getInt("errcode");
			   String errorMsg = jsonObj.getString("errmsg");
			   log.error("获取用户信息失败 errcode:{}errmasg:{}",errorCode,errorMsg);
			}
		}

		return snsUserInfo;
	}
}
