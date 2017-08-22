package com.ceyi.project.dcjewelry.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static void main(String[] args) {

		System.out.println(isMobile("23960378264"));
	}

	public static List<String> findMatchers(String reg, String str) {
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		List<String> list = new ArrayList<String>();
		if (pattern != null && str != null) {
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				String fname = matcher.group(1);
				list.add(fname);
			}
		}
		return list;
	}

	public static String cutLast(String src) {
		if (isNotEmpty(src)) {
			return src.substring(0, src.length() - 1);
		}
		return src;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumer(String str) {
		if (str != null) {
			Pattern pattern = Pattern.compile("[0-9]+");
			return pattern.matcher(str).matches();
		}
		return false;
	}

	/**
	 * 字符串数组转字符串 去除空字符串
	 * 
	 * @param str
	 * @param mobile
	 * @return
	 */
	public static String ArrayToString(String[] strs, String split) {
		String destId = "";
		for (String str : strs) {
			if (isNotEmpty(str)) {
				destId += str + split;
			}
		}
		destId = cutLast(destId);
		return destId;
	}

	/**
	 * 字符串是否在数组里
	 * 
	 * @param str
	 * @param mobile
	 * @return
	 */
	public static boolean strInArray(String[] strs, String str) {
		for (String a : strs) {
			if (a.equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串是否在集合里
	 * 
	 * @param str
	 * @param mobile
	 * @return
	 */
	public static boolean strInArray(ArrayList<String> strs, String str) {
		for (String a : strs) {
			if (a.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String number2String(int number, int length) {
		return String.format("%0" + length + "d", number);
	}

	/**
	 * 将文本域内容输出
	 * 
	 * @param text
	 * @return
	 */
	public static String textToHtml(String text) {
		if (!isEmpty(text)) {
			text = text.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
		}
		return text;
	}

	/**
	 * 过滤字符串内的SQL关键字
	 * 
	 * @param text
	 * @return
	 */
	public static String filterSql(String text) {
		if (!isEmpty(text)) {
			text = Pattern.compile("insert|update|delete|select|creat|drop|truncate\\*", Pattern.CASE_INSENSITIVE).matcher(text).replaceAll(" sql关键字 ");
		}
		return text;
	}

	public static String decode(String str) {
		if (isNotEmpty(str)) {
			try {
				str = URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 是否包含表情符号
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤表情符号
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return source;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			stringBuilder.append("%");
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	public static String decodeChinaString(String input) {
		byte[] aa = null;
		String out = "";
		String temp = "";
		if (input == null || input.length() == 0)
			return "";
		try {
			aa = input.getBytes("iso-8859-1");
			temp = bytesToHexString(aa);
			out = URLDecoder.decode(temp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	/** 
     * 手机号验证 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    } 
    
}
