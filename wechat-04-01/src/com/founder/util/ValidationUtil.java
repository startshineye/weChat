package com.founder.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *请求校验工具类
 * @author yxm
 *@version 1.0
 *@date 2016-8-4
 */
public class ValidationUtil {
	private static String token = "wxkf";
	/**
	 * 开发者通过检验signature对请求进行校验
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		// 1）将token、timestamp、nonce三个参数进行字典序排序
		String[] strArry = new String[] { token, timestamp, nonce };
		Arrays.sort(strArry);

		// 2）将三个参数字符串拼接成一个字符串
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < strArry.length; i++) {
			content.append(strArry[i]);
		}
		// 将拼接好的字符串进行sha1加密（此时用到java中的加密类MessageDigest）
		MessageDigest md = null;
		// 记录加密后结果字符串
		String result = null;
		try {
			// 获取SHA-1加密对象
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			// 将字节数组变成字符串
			result = bytesToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return result != null ? result.equals(signature.toUpperCase()) : false;
	}
	/**
	 * 将字节数组转换成十六进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String bytesToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	/**
	 * 将一个字节转换成十六进制字符串
	 * @param mbyte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		// 十六进制的十六个字符数字
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] temp = new char[2];
		temp[0] = Digit[(mByte >>> 4) & 0X0F];
		temp[1] = Digit[mByte & 0X0F];
		// 强转换后的temp构造成一个字符串
		String s = new String(temp);
		return s;
	}
}
