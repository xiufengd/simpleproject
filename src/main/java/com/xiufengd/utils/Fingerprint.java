package com.xiufengd.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Fingerprint{
	public static String md5Password(String _password,String salt) {
		_password = salt+"@"+_password;
		for(int i=1;i<=32;i++) {
			_password = fin("sha-512", _password);
			_password = fin("md5", _password);
		}
		return _password;
	}
	
	private static String fin(String finName,String src) {
		if(src == null) {
			return null;
		}
		
		byte[] digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance(finName);
			digest = md.digest(src.getBytes());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			int hi = b>>4 &0x0f;
			int lo = b &0x0f;
			char hic = (char) (hi>=10?('A'+hi-10):('0'+hi));
			char loc = (char) (lo>=10?('A'+lo-10):('0'+lo));
			sb.append(hic).append(loc);
		}
		
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(Fingerprint.md5Password("@123456", "SPDB"));
	}
}