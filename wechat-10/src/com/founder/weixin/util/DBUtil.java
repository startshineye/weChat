package com.founder.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.founder.weixin.db.model.Knowledge;

/**
 * 数据库操作类
 * @author yxm
 * @date 2016-08-16
 */
public class DBUtil {
/**
 * 获取数据库连接
 * @return Connection
 */
 private Connection getConn(){
	 Connection conn = null;
	 String url = "jdbc:mysql://localhost:3306/wechat";
	 String user = "root";
	 String password = "123";
	 try {
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//获取数据库连接
		conn = DriverManager.getConnection(url, user, password);
	 }catch (Exception e){
		e.printStackTrace();
	 }
	 return conn;
 }
 /**
  * 释放连接
  * @param conn
  * @param ps
  * @param rs
  */
private void releaseResource(Connection conn,PreparedStatement ps,ResultSet rs){
	  try {
			if(rs != null){
			rs.close();
		   }
			if(ps != null){
				ps.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
}
/**
 * 获取知识库中所有记录
 * @return
 */
public static List<Knowledge> getAllKnowledge(){
	List<Knowledge> arrayList = new ArrayList<Knowledge>();
	String sql = "select * from knowledge";
	DBUtil dbutil = new DBUtil();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try{
	conn = dbutil.getConn();
	ps = conn.prepareStatement(sql);
    rs = ps.executeQuery();
    while(rs.next()){
      Knowledge knowledge = new	Knowledge();
      knowledge.setId(rs.getInt("id"));
      knowledge.setQuestion(rs.getString("question"));
      knowledge.setAnswer(rs.getString("answer"));
      knowledge.setCategory(rs.getInt("category"));
      arrayList.add(knowledge);
    }
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		dbutil.releaseResource(conn, ps, rs);
	}
	return arrayList;
}
/**
 * 获取用户上一次聊天类别
 * @param userId
 * @return chatCategory
 */
public static int getLastCategory(String userId){
		int chatCategory = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select chat_category from chat_log where userid = ? order by id desc limit 0,1";
		DBUtil dbUtil = new DBUtil();
		try {
			conn = dbUtil.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while(rs.next()){
				chatCategory = rs.getInt("chat_category");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbUtil.releaseResource(conn, ps, rs);
		}
		return chatCategory;
}
/**
 * 根据knowledgeId随机获取一个答案
 * @param knowledgeId
 * @return knowlegeAnswer
 */
public static String getRandKnowledge(int knowledgeId){
	String knowlegeAnswer = "";
	String sql = "select answer from knowledge_sub where pid = ? order by rand() limit 0,1";
	Connection conn=null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	DBUtil dbUtil = new DBUtil();
	try{
		conn = dbUtil.getConn();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(1, knowledgeId);
	    rs = ps.executeQuery();
	    if(rs.next()){
	      knowlegeAnswer = rs.getString("answer");
	    }
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		dbUtil.releaseResource(conn, ps, rs);
	}
	return knowlegeAnswer;
}
/**随机获取一条笑话
 * @return jokeContent
 */
public static String getJoke(){
	String jokeContent = "";
	String sql = "select joke_content from joke order by rand() limit 0,1";
	Connection conn=null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	DBUtil dbUtil = new DBUtil();
	try{
		conn = dbUtil.getConn();
	    ps = conn.prepareStatement(sql);
	    rs = ps.executeQuery();
	    if(rs.next()){
	    	jokeContent = rs.getString("joke_content");
	    }
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		dbUtil.releaseResource(conn, ps, rs);
	}
	return jokeContent;
}
/**
 * 保存聊天记录
 * @param userId
 * @param createTime
 * @param reqMsg
 * @param respMsg
 * @param chatCategory
 */
public static void saveChat(String userId,String createTime,String reqMsg,String respMsg,int chatCategory){
	String sql = "insert into chat_log(userid,createtime,req_msg,resp_msg,chat_category)values(?,?,?,?,?)";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	DBUtil dbUtil = new DBUtil();
	try{
		conn = dbUtil.getConn();
		ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		ps.setString(2, createTime);
		ps.setString(3, reqMsg);
		ps.setString(4, respMsg);
		ps.setInt(5, chatCategory);
		ps.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		dbUtil.releaseResource(conn, ps, rs);
	}
}
}
