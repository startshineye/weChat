package com.founder.weixin.message.resp;
/**
 * 响应消息基类
 * @author yxm
 * @date 2016-8-4
 */
public class BaseMessage {
	//接收方帐号（收到的OpenID）
	private String ToUserName;	
	//开发者微信号
	private String FromUserName;	
	//消息创建时间 （整型）
	private Long CreateTime;	
	//消息类型
	private String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
