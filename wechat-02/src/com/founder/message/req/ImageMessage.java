package com.founder.message.req;
/**
 * 圖片消息
 * @author yxm
 * @date 2016-8-4
 */
public class ImageMessage extends BaseMessage{
	//图片链接（由系统生成）
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
