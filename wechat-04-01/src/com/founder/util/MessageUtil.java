package com.founder.util;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.founder.message.model.Article;
import com.founder.message.resp.ImageMessage;
import com.founder.message.resp.MusicMessage;
import com.founder.message.resp.NewsMessage;
import com.founder.message.resp.TextMessage;
import com.founder.message.resp.VideoMessage;
import com.founder.message.resp.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息处理类
 * @author yxm
 * @date 2016-8-4
 */
public class MessageUtil {
 // 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "scan";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";

	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
  /**
   * 解析微信服务器发过来的请求（XML），将XML解析成文本放在map中
   * @param request
   * @return
   * @throws Exception
   */
  public static Map<String,String> parseXml(HttpServletRequest request) throws Exception{
	 Map<String,String> map = new HashMap<String,String>();
	 //获取输入流
	 InputStream in = request.getInputStream();
	 //使用DOM4J解析
	 SAXReader reader = new SAXReader();
	 //生成解析文档对象
	 Document doc = reader.read(in);
	 //获取根节点
	 Element root = doc.getRootElement();
	 //通过根节点获取所有子节点
	 List<Element> elements = root.elements();
	 
	 //节点名作为hashMap的键，节点值作为hashMap的值
	  for (Element element : elements) {
		map.put(element.getName(),element.getText());
	}
	  //关闭流
	  in.close();
	  return map;
  }
  //扩展系统库里面的类(处理CDATE)
  private static XStream xstream = new XStream(new XppDriver(){
	 public HierarchicalStreamWriter createWriter(Writer out){
		 return new PrettyPrintWriter(out){
			 boolean cdata = true;
			 public void startNode(String name,Class clazz){
				 super.startNode(name,clazz);
			 }
			 protected void writeText(QuickWriter writer,String text){
				 if(cdata){
					writer.write("<![CDATA[");
					writer.write(text);
					writer.write("]]>");
				 }else{
					 writer.write(text); 
				 }
			 }
		 };
	 } 
  });
  /**
   * 将文本消息转换成XML格式
   * @param textMessage
   * @return
   */
  public static String messageToXml(TextMessage textMessage){
	  xstream.alias("xml", textMessage.getClass());
	  return xstream.toXML(textMessage);
  }
/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMessage 图片消息对象
	 * @return xml
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 语音消息对象转换成xml
	 * 
	 * @param voiceMessage 语音消息对象
	 * @return xml
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 视频消息对象转换成xml
	 * 
	 * @param videoMessage 视频消息对象
	 * @return xml
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}