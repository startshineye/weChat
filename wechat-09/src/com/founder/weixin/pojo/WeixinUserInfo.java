package com.founder.weixin.pojo;
/**
 * 微信用户的基本信息
 * @author yxm
 * @date 2016-08-11
 */
public class WeixinUserInfo {
	// 用户订阅号
	private int subscribe;
	// 用户标识
	private String openId;
	// 用户昵称
	private String nickname;
	// 用户性别
	private int sex;
	// 城市
	private String city;
	// 国家
	private String country;
	// 省份
	private String province;
	// 语言
	private String language;
	// 用户头像
	private String headImgUrl;
	// 关注时间
	private String subscribeTime;
	// 用户unionid
	private String unionid;
	// 备注
	private String remark;
	// 用户分组id
	private String groupId;

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "WeixinUserInfo [city=" + city + ", country=" + country
				+ ", groupId=" + groupId + ", headImgUrl=" + headImgUrl
				+ ", language=" + language + ", nickname=" + nickname
				+ ", openId=" + openId + ", province=" + province + ", remark="
				+ remark + ", sex=" + sex + ", subscribe=" + subscribe
				+ ", subscribeTime=" + subscribeTime + ", unionid=" + unionid
				+ "]";
	}
}
