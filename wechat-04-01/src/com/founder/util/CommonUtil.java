package com.founder.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.pojo.Token;


/**
 * 通用工具类
 * @author yxm
 * @date 2016-08-09
 */
public class CommonUtil {
   //定义日志
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	//凭证获取
	public final static  String token_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
   /**
    * 发送https请求
    * @param requestUrl 请求地址
    * @param requestMethod 请求方式（GET,POST）
    * @param outputStr 提交数据
    * @return jsonObject
    */
	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
    	JSONObject jsonObject = null;
    		try {
				//创建SSLContext对象
				TrustManager[] tm = {new MyX509TrustManager()};
				SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
				sslContext.init(null,tm,new java.security.SecureRandom());
				//从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				
				URL url = new URL(requestUrl);
				HttpsURLConnection conn =  (HttpsURLConnection)url.openConnection();
				conn.setSSLSocketFactory(ssf);
				
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				//设置请求方式（GET/POST）
				conn.setRequestMethod(requestMethod);
				
				//当outputStr不为null时，向输出流写数据
				if(outputStr != null){
				   OutputStream outputStream = conn.getOutputStream();
				   //设置编码格式
				   outputStream.write(outputStr.getBytes("UTF-8"));
				   outputStream.close();
				}
				
				//从输入流读取返回内容
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				StringBuffer stringBuffer = new StringBuffer();
				while((str = bufferedReader.readLine())!= null){
					stringBuffer.append(str);
				}
				//释放资源
				bufferedReader.close();
				inputStream.close();
				inputStreamReader.close();
				inputStream = null;
				conn.disconnect();
				jsonObject = JSONObject.fromObject(stringBuffer.toString());
			} catch (Exception e) {
				log.error("https 请求异常",e);
			}  
    	return jsonObject;
    }
	
	
	public static Token getToken(String appid,String appsecret){
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		//发送get请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET",null);
		
		if(jsonObject != null){
			try{
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expire_in"));
			}catch(JSONException e){
				token = null;
				//获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
			
		}
		return token;
	}
}




