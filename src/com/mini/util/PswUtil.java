package com.mini.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * a tool class used to encrypt password
 * 
 * @author Captain
 * 
 */
public class PswUtil {

	/**
	 * encrypt password to a sha1 String of 40 bytes
	 * @param str password
	 * @return encryption
	 */
	public static String str2Sha1(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytes2HexString(md.digest());
	}

	private static String bytes2HexString(byte[] b) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret.append(hex.toUpperCase());
		}
		return ret.toString();
	}

	/**
	 * @return a random sequence of 6 bytes
	 */
	public static String getRandomSalt() {
		return getRandomStr(6);
	}

	/**
	 * encrypt password to a sha1 String of 40 bytes
	 * @param str password
	 * @param salt
	 * @return encryption
	 */
	public static String str2Sha1(String str, String salt) {
		return str2Sha1(str + salt);
	}

	/**
	 * generate random sequence consist of letter and number
	 * @param length :the size of random sequence
	 * @return 
	 */
	public static String getRandomStr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean charOrNum = random.nextInt(2) % 2 == 0 ? true : false;
			if (charOrNum) {
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

}
