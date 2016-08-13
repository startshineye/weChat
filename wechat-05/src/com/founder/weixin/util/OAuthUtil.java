package com.founder.weixin.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.founder.weixin.message.model.Article;
import com.founder.weixin.message.model.Music;
import com.founder.weixin.pojo.SNSUserInfo;
import com.founder.weixin.pojo.WeixinOauth2Token;

 

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
				log.error("获取网页授权凭证失败 errcode:{}errmsg:{}",errorCode,errorMsg);
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
				log.error("刷新网页授权凭证失败 errcode:{}errmsg:{}",errorCode,errorMsg);
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
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID",
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
	
	/**
	 * 组装文本客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param content 文本消息内容
	 * @return
	 */
	public static String makeTextCustomMessage(String openId, String content) {
		// 对消息内容中的双引号进行转义
		content = content.replace("\"", "\\\"");
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId, content);
	}

	/**
	 * 组装图片客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return
	 */
	public static String makeImageCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	/**
	 * 组装语音客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	/**
	 * 组装视频客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @param thumbMediaId 视频消息缩略图的媒体id
	 * @return
	 */
	public static String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId, thumbMediaId);
	}

	/**
	 * 组装音乐客服消息
	 * @param openId 消息发送对象
	 * @param music 音乐对象
	 * @return
	 */
	public static String makeMusicCustomMessage(String openId, Music music) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
		jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
		// 将jsonMsg中的thumbmediaid替换为thumb_media_id
		jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
		return jsonMsg;
	}

	/**
	 * 组装图文客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param articleList 图文消息列表
	 * @return
	 */
	public static String makeNewsCustomMessage(String openId,List<Article>articleList) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
		jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
		// 将jsonMsg中的picUrl替换为picurl
		jsonMsg = jsonMsg.replace("picUrl", "picurl");
		return jsonMsg;
	}

	/**
	 * 发送客服消息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
	 * @return true | false
	 */
	public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
		log.info("消息内容：{}", jsonMsg);
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 发送客服消息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("客服消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}

		return result;
	}
}
