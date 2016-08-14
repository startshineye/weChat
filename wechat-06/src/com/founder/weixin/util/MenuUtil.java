package com.founder.weixin.util;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.weixin.menu.Menu;
/**
 * 菜单工具类（实现对菜单创建，查询，删除）
 * @author yxm
 * @date 2016-08-09
 */
public class MenuUtil {
   private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
   //菜单创建http请求方式：POST
   private static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
 //菜单查询http请求方式：GET
   private static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
 //菜单删除http请求方式：GET
   private static  String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"; 
   /**
    * 创建菜单
    * @param menu 菜单实例
    * @param accessToken 凭证
    * @return true 成功 
    */
   public static boolean createMenu(Menu menu,String accessToken){
	   boolean result = false;
	   String url = menu_create_url.replace("ACCESS_TOKEN",accessToken);
	   //将菜单对象转化为JSON字符串
	   String jsonMenu = JSONObject.fromObject(menu).toString();
	   //发起post请求创建菜单
	   JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST",jsonMenu);
	   
	   if(jsonObject != null){
		   int errorCode = jsonObject.getInt("errcode");
		   String errorMsg = jsonObject.getString("errmsg");
		   if(errorCode == 0){
			   result  = true;
		   }else{
			   result = false;
			   log.error("创建菜单失败 errcode:{} errmsg:{}",errorCode,errorMsg);
		   }
	   }
	   return result;
   }
   /**
    * 查询菜单
    * @param accessToken
    * @return 
    */
   public static String getMenu(String accessToken){
	   String result = null;
	   String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
	   //发起get请求
	   JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
	   
	   if(jsonObject != null){
		   result = jsonObject.toString();
	   }
	   return result;
   }
    /**
     * 删除菜单
     * @param accessToken
     * @return true 成功
     */
   public static boolean deleteMenu(String accessToken){
	   boolean result = false;
	   String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
	   //发请GET请求删除菜单
	   JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
	   
	   if(jsonObj != null){
		   int errorCode = jsonObj.getInt("errcode");
		   String errorMsg = jsonObj.getString("errmsg");
		   if(errorCode == 0){
			   result = true;
		   }else{
			   result = false;
			   log.error("删除菜单失败 errcode:{} errmsg:{}",errorCode,errorMsg); 
		   }
	   }
	   return result;
   }
}
