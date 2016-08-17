package com.founder.weixin.message.resp;
/**
 * 回复图片消息
 * @author yxm
 * @date 2016-8-4
 */
public class ImageMessage extends BaseMessage{
	//通过素材管理中的接口上传多媒体文件，得到的id;
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}	  
}
