package com.whpe.qrcode.jiangxi_jian.bigtools;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by sheep on 2016/6/12.
 */
public class IDUtils {

	/**
	 * 身份证号有效性验证，15/18位验证
	 * @param id 待验证身份证号
	 * @return 身份证号有效返回true，否则false
	 */
	public static boolean isID(final String id)
	{
		boolean isOk = false;

		if((null == id) || (id.isEmpty())) return isOk;


		if((15 == id.length()) || (18 == id.length())) {
			Pattern idPattern = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)"); //15位全数字或17位全数字加最后X或数字

			Matcher isMatch = idPattern.matcher(id);

			isOk = isMatch.matches();
		}
		return isOk;
	}

	/**
	 * 将身份证号统一填充为18位，不足右补“ ”
	 * @param id
	 * @return
	 */
	public static String toFullID(final String id){
		String sId = id;
		for(int i = 0; i < (18 - id.length()); i++){
			sId += " ";
		}
		return sId;
	}


	/**
	 * 判断是否为手机号
	 * @param phoneno
	 * @return
	 */
	public static boolean isMobileNO(final String phoneno){
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p = Pattern.compile("^(1[3-9])\\d{9}$");//手机号码太多，13-19开头基本都有
		Matcher m = p.matcher(phoneno);

		return m.matches();
	}


	/**
	 * 检测邮箱地址是否合法
	 * @param email
	 * @return true合法 false不合法
	 */
	public static boolean isEmail(String email){
		if (null==email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
