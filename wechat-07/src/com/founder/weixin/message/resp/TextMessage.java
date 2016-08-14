package com.founder.weixin.message.resp;
/**
 * 回复文本消息
 * @author yxm
 * @date 2016-8-4
 */
public class TextMessage extends BaseMessage{
	//回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
