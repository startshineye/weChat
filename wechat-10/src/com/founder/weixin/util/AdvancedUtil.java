package com.founder.weixin.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.weixin.message.model.Article;
import com.founder.weixin.message.model.Music;
import com.founder.weixin.pojo.SNSUserInfo;
import com.founder.weixin.pojo.WeixinGroup;
import com.founder.weixin.pojo.WeixinOauth2Token;
import com.founder.weixin.pojo.WeixinUserInfo;
import com.founder.weixin.pojo.WeixinUserList;

 

/**
 * 微信高级接口工具类
 * @author yxm
 * @ date 2016-08-10
 */
public class AdvancedUtil {
	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
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
	 * 获取用户基本
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static WeixinUserInfo getUserInfo(String accessToken,String openId){
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);

		// 获取用户信息（返回JSONObject）
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (jsonObj != null) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 给对象赋值
				weixinUserInfo.setCity(jsonObj.getString("city"));
				weixinUserInfo.setCountry(jsonObj.getString("country"));
				weixinUserInfo.setGroupId(jsonObj.getString("groupid"));
				weixinUserInfo.setHeadImgUrl(jsonObj.getString("headimgurl"));
				weixinUserInfo.setLanguage(jsonObj.getString("language"));
				weixinUserInfo.setNickname(jsonObj.getString("nickname"));
				weixinUserInfo.setOpenId(jsonObj.getString("openid"));
				weixinUserInfo.setProvince(jsonObj.getString("province"));
				weixinUserInfo.setRemark(jsonObj.getString("remark"));
				weixinUserInfo.setSex(jsonObj.getInt("sex"));
				weixinUserInfo.setSubscribe(jsonObj.getInt("subscribe"));
				weixinUserInfo.setSubscribeTime(jsonObj.getString("subscribe_time"));
				weixinUserInfo.setUnionid("unionid");
			}  catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObj.getInt("errcode");
					String errorMsg = jsonObj.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}
	/**
	 * 获取用户关注列表
	 * @param accessToken
	 * @param nextOpenId
	 * @return WeixinUserList
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static WeixinUserList getUserList(String accessToken,String nextOpenId){
		WeixinUserList weixinUserList = null;
		
		if(nextOpenId == null)
			nextOpenId = null;
		//拼接url
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN",accessToken).replace("NEXT_OPENID", nextOpenId);
		//获取关注者列表
		 JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		 
		 if(jsonObj != null){
			System.out.println("获取到的json列表");
			System.out.println(jsonObj.toString());
			try{
			 //如果获取到关注者列表，则封装成java对象
			weixinUserList = new WeixinUserList(); 
			weixinUserList.setCount(jsonObj.getInt("count"));
			weixinUserList.setNext_openid(jsonObj.getString("next_openid"));
			weixinUserList.setTotal(jsonObj.getInt("total"));
			JSONObject dataObj = (JSONObject) jsonObj.get("data");
			weixinUserList.setOpenIdList(JSONArray.toList(dataObj.getJSONArray("openid"),List.class));
			}catch (Exception e) {
				weixinUserList = null;
				System.out.println("错误回复值：");
				System.out.println(jsonObj.toString());
				int errorCode = jsonObj.getInt("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("获取关注者列表失败 errorcode:{} errmsg:{}",errorCode,errorMsg);
			}
			}
		 return weixinUserList;
	}
	
   /**
    * 查询分组
    * @param accessToken
    * @return List<WeixinGroup>
    */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public  static List<WeixinGroup> getGroup(String accessToken){
		List<WeixinGroup> weixinGroupList = null;
		//拼接url
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		
		//发送http的Get请求获取分组列表
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		//如果获得数据则封装成List对象用于返回
		if(jsonObj != null){
			try{
			//获得jsonArray数组
			JSONArray jsonArray = jsonObj.getJSONArray("groups");
			System.out.println("jsonArry为："+jsonArray.toString());
			
			//将JSONArray数组转化成List
			weixinGroupList = JSONArray.toList(jsonArray,WeixinGroup.class);
			}catch(Exception  e){
			 //发生异常时，输出异常信息
				weixinGroupList = null;
				int errorCode = jsonObj.getInt("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("查询分组失败  errorcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return weixinGroupList;
	}
	/**
	 * 创建分组
	 * @param accessToken
	 * @param groupName
	 * @return WeixinGroup
	 */
	public static WeixinGroup createGroup(String accessToken,String groupName){
		WeixinGroup weixinGroup = null;
		//拼接url
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		
		//POST数据例子：{"group":{"name":"test"}}
		String jsonData = "{\"group\":{\"name\":\"%s\"}}";
		//发送请求
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, groupName));
		
		if(jsonObj != null){
			try {
               weixinGroup = new WeixinGroup();
               weixinGroup.setId(jsonObj.getJSONObject("group").getInt("id"));
               weixinGroup.setName(jsonObj.getJSONObject("group").getString("name"));
			} catch (Exception e) {
				 //发生异常时，输出异常信息
				weixinGroup = null;
				int errorCode = jsonObj.getInt("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("查询分组失败  errorcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return weixinGroup;
	}
	/**
	 * 修改分组
	 * @param accessToken
	 * @param groupId
	 * @param groupName
	 * @return true/false
	 */
	public static boolean updateGroup(String accessToken,int groupId,String groupName){
		boolean result = false;
		//拼接url
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN",accessToken);
		//POST数据例子：{"group":{"id":108,"name":"test2_modify2"}}
		String jsonData = "{\"group\":{\"id\":%d,\"name\":\"%s\"}}";
		//修改分组名
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData,groupId,groupName));
		if(jsonObj != null){
			//{"errcode": 0, "errmsg": "ok"}
			 int errorCode = jsonObj.getInt("errcode");
			 String errorMsg = jsonObj.getString("errmsg");
			 if(errorCode == 0){
				 result = true;
				 log.info("修改分组成功 errcode:{} errmsg:{}",errorCode,errorMsg);
			 }else{
				 log.info("修改分组失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			 }
		}
		return result;
	}
	/**
	 * 移动用户分组
	 * @param accessToken
	 * @param openId
	 * @param groupId
	 * @return
	 */
	public static boolean updateMemberGroup(String accessToken,String openId ,int groupId){
		boolean result = false;
		//拼接url
		String requestUrl  = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		//POST数据例子：{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
		String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
		//移动用户分组
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl,"POST",String.format(jsonData,openId,groupId));
		System.out.println(jsonObj.toString());
		
		if(jsonObj != null){
			//{"errcode": 0, "errmsg": "ok"}
			int errorCode = jsonObj.getInt("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			 if(errorCode == 0){
				 result = true;
				 log.info("移动分组成功 errcode:{} errmsg:{}",errorCode,errorMsg);
			 }else{
				 log.info("移动分组失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			 }
		}
		return result;
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
