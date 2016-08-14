package com.founder.weixin.menu;
/**
 * 复合类型按钮（指含有子菜单的按钮button）
 * 
 * @author yxm
 * @date 2016-08-09
 * 
 */
public class ComplexButton extends Button{
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] subButton) {
		sub_button = subButton;
	}

}
