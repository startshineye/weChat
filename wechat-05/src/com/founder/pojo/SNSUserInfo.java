package com.founder.pojo;

import java.util.List;

/**
 * 网页授权后得到的用户消息（网页授权成功后会返回以下属性的json）
 * @author yxm
 * @date 2016-08-10
 */
public class SNSUserInfo {
  //用户标识
	private String openId;
	//用户昵称
	private String nickname;
	//用户性别
	private int sex;
	//用户省份
	private String province;
	//城市
	private String city;
	//国家
	private String country;
	//头像连接地址
	private String headimgurl;
	//用户特权信息
	private List<String> privilegeList;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public List<String> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	
}
