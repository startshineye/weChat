package com.founder.weixin.message.resp;

import com.founder.weixin.message.model.Video;

/**
 * 视频消息
 * @author yxm
 * @date 2016-8-4
 */
public class VideoMessage extends BaseMessage{
	// 视频
	private Video vidoe;

	public Video getVidoe() {
		return vidoe;
	}
	public void setVidoe(Video vidoe) {
		this.vidoe = vidoe;
	}

}
