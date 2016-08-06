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

import com.founder.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.core.util.QuickWriter;

/**
 * 消息处理类
 * @author yxm
 * @date 2016-8-4
 */
public class MessageUtil {
  //响应消息类型：文本
  public static final String RESP_MESSAGE_TYPE_TEXT="text";
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
					writer.write("]]");
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
}
