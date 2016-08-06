
package com.founder.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.founder.message.resp.TextMessage;
import com.founder.util.MessageUtil;

/**
 * 核心服务类（处理我们发送给微信服务器请求）
 * @author yxm
 * @date 2016-8-4
 */
public class ProcessService {
  public static String dealRequest(HttpServletRequest request) throws Exception{
	  String respXml = "";
	  String respContent = "位置数据类型";
	  
	  Map<String,String> requestMap = MessageUtil.parseXml(request);
	  
	  String fromUsserName = requestMap.get("FromUsserName");
	  String toUserName = requestMap.get("ToUsserName");
	  String MsgType = requestMap.get("MsgType");
	  
	  //组装返回的文本
	  TextMessage textMessage = new TextMessage();
	  textMessage.setToUserName(fromUsserName);
	  textMessage.setFromUserName(toUserName);
	  textMessage.setCreateTime(new Date().getTime());
	  textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
  }
}
