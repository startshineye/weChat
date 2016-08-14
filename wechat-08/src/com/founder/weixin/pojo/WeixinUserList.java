package com.founder.weixin.pojo;

import java.util.List;

/**
 * 关注用户列表类
 * @author yxm
 * @date 2016-08-12
 *
 */
public class WeixinUserList {
	//关注该公众账号的总用户数
	private int total;
	//拉取的OPENID个数，最大值为10000
	private int count;
	//列表数据，OPENID的列表
	private List<String> openIdList;
	//拉取列表的最后一个用户的OPENID 
	private String next_openid;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getOpenIdList() {
		return openIdList;
	}
	public void setOpenIdList(List<String> openIdList) {
		this.openIdList = openIdList;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String nextOpenid) {
		next_openid = nextOpenid;
	}
	@Override
	public String toString() {
		return "WeixinUserList [count=" + count + ", next_openid="
				+ next_openid + ", openIdList=" + openIdList + ", total="
				+ total + "]";
	}
	
}
