package com.mini.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 处理ID集合与字符串相互转化的工具类
 * @author 雷晓冰 2014-11-011
 */
public class IdListUtil {
	/**
	 * 将ID字符串转化为集合
	 * @param string
	 * @return
	 */
	public static List<Integer> string2List(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string, "&&");
		List<Integer> list = new ArrayList<Integer>();
		while(tokenizer.hasMoreElements()) {
			list.add(Integer.valueOf(tokenizer.nextToken()));
		}
		return list;
	}
}
