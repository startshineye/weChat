package com.founder.weixin.message.req;
/**
 * 文本消息
 * @author yxm
 * @date 2016-8-4
 */
public class TextMessage extends BaseMessage{
	// 文本消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
  
}
