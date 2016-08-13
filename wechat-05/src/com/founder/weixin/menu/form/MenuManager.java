package com.founder.weixin.menu.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.weixin.menu.Button;
import com.founder.weixin.menu.ClickButton;
import com.founder.weixin.menu.ComplexButton;
import com.founder.weixin.menu.Menu;
import com.founder.weixin.menu.ViewButton;
import com.founder.weixin.pojo.Token;
import com.founder.weixin.util.CommonUtil;
import com.founder.weixin.util.MenuUtil;

/**
 * 菜单管理类（定义菜单结构，并且创建菜单）
 * @author yxm
 * @date 2016-08-09
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	/**
	 * 菜单结构定义
	 * @return
	 */
	private static Menu getMenu() {
       //构造二级click菜单
		ClickButton btn11 = new ClickButton();
		btn11.setName("方正");
		btn11.setType("click");
		btn11.setKey("founder");
		
		ClickButton btn12 = new ClickButton();
		btn12.setName("方正国际");
		btn12.setType("click");
		btn12.setKey("founderNational");
		
		ViewButton btn13 = new ViewButton();
		btn13.setName("用户授权");
		btn13.setType("view");
		//访问的是m站点
		btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx73d9bb98d3a0175f&redirect_uri=http%3A%2F%2F99bc8d18.ngrok.io%2Fwxkf%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton btn21 = new ViewButton();
		btn21.setName("淘宝");
		btn21.setType("view");
		//访问的是m站点
		btn21.setUrl("http://m.taobao.com");
		
		ViewButton btn22 = new ViewButton();
		btn22.setName("京东");
		btn22.setType("view");
		btn22.setUrl("http://m.jd.com");
		
		ViewButton btn31 = new ViewButton();
		btn31.setName("多泡");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");
		
		
		ViewButton btn32 = new ViewButton();
		btn32.setName("一窝 88");
		btn32.setType("view");
		btn32.setUrl("http://www.yi588.com");
		
		ComplexButton btn1 = new ComplexButton();
		btn1.setName("方正集团");
		btn1.setSub_button(new Button[]{btn11,btn12,btn13});
	
		ComplexButton btn2 = new ComplexButton();
		btn2.setName("方正购物");
		btn2.setSub_button(new Button[]{btn21,btn22});
		
		ComplexButton btn3 = new ComplexButton();
		btn3.setName("方正网游");
		btn3.setSub_button(new Button[]{btn31,btn32});
		
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn1,btn2,btn3});
		
		return menu;
	}
	/**
	 * 生成菜单
	 * @param args
	 */
	public static void main(String[] args){
		//第三方用户唯一凭证
		String appId = "wx73d9bb98d3a0175f";
		//第三方用户唯一密码
		String appSecret = "19fe03289d3091a1e5476e35f59c620e";
		
		//调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);
		if(token != null){
			//创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
			
			//判断菜单创建结果
			if(result){
				log.info("菜单创建成功!");
			}else{
				log.info("菜单创建失败！");
			}
		}
	}
}
