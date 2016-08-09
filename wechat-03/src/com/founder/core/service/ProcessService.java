
package com.founder.core.service;

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
	  //XML格式消息数据
	  String respXml = "";
	  //默认返回的消息内容
	  String respContent = "未知数据类型";
	  //解析消息
	  Map<String,String> requestMap = MessageUtil.parseXml(request);
	  //发送方账号，开发者微信号，消息类型
	  String fromUserName = requestMap.get("FromUserName");
	  String toUserName = requestMap.get("ToUserName");
	  String msgType = requestMap.get("MsgType");
	  System.out.println("开发者："+toUserName+"发送者："+fromUserName+"消息类型："+msgType);
	  //返回文本消息
	  TextMessage textMessage = new TextMessage();
	  textMessage.setToUserName(fromUserName);
	  textMessage.setFromUserName(toUserName);
	  textMessage.setCreateTime(new Date().getTime());
	  textMessage.setMsgType(msgType);
	  
	  //文本消息
	  if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
		  respContent="您发送的是文本消息";
	  }//图片消息
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
		  respContent="您发送的是图片消息";
	  }//语音消息
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
		  respContent="您发送的是语音消息";
	  }//视频
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
		  respContent="您发送的是视频消息";
	  }//地理位置
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
		  respContent="您发送的是地理位置消息";
	  }//链接消息
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
		  respContent="您发送的是链接消息";
	  }//事件推送
	  else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
		  //事件类型
		  String eventType = requestMap.get("Event");
		  //关注
		  if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
			  respContent = "谢谢您关注";
		  }//取消关注
		  else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
			  //TODO
		  }//扫描带参数二维码
		  else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
			  //TODO
		  }//上报地理位置
		  else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){
			  //TODO
		  }//自定义菜单
		  else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
			  //TODO
		  }
	  }
	  
	  //设置文本消息内容
	  textMessage.setContent(respContent);
	  //将文本消息对象转换为XML
	  respXml=MessageUtil.messageToXml(textMessage);
	  System.out.println("respXml:");
	  System.out.println(respXml);
	  return respXml;
  }
}
