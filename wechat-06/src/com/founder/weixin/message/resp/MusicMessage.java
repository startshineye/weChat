package com.founder.weixin.message.resp;

import com.founder.weixin.message.model.Music;

/**
 * 响应音乐消息
 * @author yxm
 * @date 2016-8-4
 */
public class MusicMessage extends BaseMessage{
	// 音乐
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
}
