package org.konghao.cms.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.konghao.basic.util.SecurityUtil;

public class TestMd5 {
	public static void main(String[] args) {
		String password = "abcd";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes(), 0, password.length());
			BigInteger bi = new BigInteger(1, md.digest());
			System.out.println(bi.toString(16));
			System.out.println(SecurityUtil.md5(password));
			System.out.println("-------------------------");
			System.out.println(SecurityUtil.md5("admin",password));
			System.out.println(SecurityUtil.md5("admin1",password));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
}
