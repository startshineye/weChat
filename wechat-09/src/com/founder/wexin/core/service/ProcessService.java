
package com.founder.wexin.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.founder.weixin.message.model.Article;
import com.founder.weixin.message.resp.NewsMessage;
import com.founder.weixin.message.resp.TextMessage;
import com.founder.weixin.pojo.Location;
import com.founder.weixin.util.MessageUtil;

/**
 * 核心服务类（处理我们发送给微信服务器请求）
 * @author yxm
 * @date 2016-8-4
 */
public class ProcessService {
	/**
	 * 处理微信发来的请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
  public static String dealRequest(HttpServletRequest request){
	// xml格式的消息数据
		String respXml = null;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			System.out.println("消息类型："+msgType);

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				
				System.out.println("事件类型："+eventType);
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx73d9bb98d3a0175f&redirect_uri=http%3A%2F%2Ff8f6d81e.ngrok.io%2Fwxkf%2FcoreServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
					// 将消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 暂不做处理
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					
					System.out.println("EventKey:"+eventKey);
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("founder")) {
						Article article = new Article();
						article.setTitle("方正");
						article.setDescription("方正集团由北京大学于1986年投资创办。\n\n 王选院士为方正集团技术决策者、奠基人，其发明的汉字激光照排技术奠定了方正集团起家之业。\n\n 方正集团拥有并创造了对中国IT，医疗医药产业发展至关重要的核心技术，吸引多家国际资本注入，目前已成为中国信息产业前三强的大型控股集团，\n\n 业务领域涵盖IT、医疗医药、房地产、金融、大宗商品贸易等产业");
						article.setPicUrl("");
						article.setUrl("http://www.founder.com/");
						List<Article> articleList = new ArrayList<Article>();
						articleList.add(article);
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.messageToXml(newsMessage);
					} else if (eventKey.equals("founderNational")) {
						Article article = new Article();
						article.setTitle("方正国际");
						article.setDescription("方正国际软件有限公司，依托北京大学和方正集团，致力于成为一流的软件和信息技术服务商。\n\n 公司现有员工4000余人，拥有4个研发基地，2个合作研究中心，在北京、苏州、武汉、广州、东京等地建有前方交付平台和研发基地。\n\n 立足于“产学研用”的发展模式，方正国际始终坚持走自主创新之路，业务重点聚焦于金融、公安和地理信息、医疗卫生、智能交通、媒体等五大行业，全面服务城市信息化建设。\n\n 作为国家认定的高新技术企业，方正国际客户遍及中、日、东南亚、北美、欧洲、中东等多个国家和地区，已快速成长为有影响力的一站式解决方案提供商 http://www.founderinternational.com/");
						article.setPicUrl("");
						article.setUrl("http://www.founderinternational.com/");
						List<Article> articleList = new ArrayList<Article>();
						articleList.add(article);
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respXml = MessageUtil.messageToXml(newsMessage);
					}
					//上报地理位置事件
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){
					//Latitude地理位置纬度
					String latitude = requestMap.get("Latitude");
					//Longitude地理位置经度
					String longitude = requestMap.get("Longitude");
					//Precision地理位置精度 
					String precision = requestMap.get("Precision");
					
					Location location = new Location();
					location.setLatitude(latitude);
					location.setLongitude(longitude);
					location.setPrecision(precision);
					
					request.setAttribute("location",location);
					System.out.println(location);	
				}
			}
			// 当用户发消息时
			else {
				textMessage.setContent("请通过菜单使用网址导航服务！");
				respXml = MessageUtil.messageToXml(textMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
