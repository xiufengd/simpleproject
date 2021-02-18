package com.xiufengd.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("restriction")
public class EncryptUtil {
	// 密钥是16位长度的byte[]进行Base64转换后得到的字符串
	public static String key = "LmMGStGtOpF4xNyvYt54EQ==";
	public static String EVIDENCEDATE = "20180901";
	public static String key16 = "dG9fd2VjcmVkbw==";
	private static SecretKeySpec deskey ;
	private static IvParameterSpec ivParam;
	private static byte[] keyArr = new byte[8];
	private static byte[] ivArr = new byte[8];
	
	static{
		getKeyIV("LmMGStGtOpF4xNyvYt54EQ==", keyArr, ivArr);
		deskey = new SecretKeySpec(keyArr, "DES");
		ivParam = new IvParameterSpec(ivArr);
	}
	
	public static String encrypt(String xmlStr) {
		byte[] encrypt = null;

		try {
			// 取需要加密内容的utf-8编码。
			encrypt = xmlStr.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 取MD5Hash码，并组合加密数组
		byte[] md5Hasn = null;
		try {
			md5Hasn = EncryptUtil.MD5Hash(encrypt, 0, encrypt.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 组合消息体
		byte[] totalByte = EncryptUtil.addMD5(md5Hasn, encrypt);

		// 取密钥和偏转向量
//		getKeyIV(EncryptUtil.key, keyArr, iv);
//		SecretKeySpec deskey = new SecretKeySpec(keyArr, "DES");
//		IvParameterSpec ivParam = new IvParameterSpec(ivArr);

		// 使用DES算法使用加密消息体
		byte[] temp = null;
		try {
			temp = EncryptUtil.DES_CBC_Encrypt(totalByte, deskey, ivParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 使用Base64加密后返回
		return Base64.encodeBase64String(temp);
	}

	
	public static String decrypt(String xmlStr) throws Exception {
		// base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] encBuf = null;
		try {
			encBuf = decoder.decodeBuffer(xmlStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 取密钥和偏转向量
//		byte[] key = new byte[8];
//		byte[] iv = new byte[8];
//		getKeyIV(EncryptUtil.key, key, iv);

		

		// 使用DES算法解密
		byte[] temp = null;
		try {
			temp = EncryptUtil.DES_CBC_Decrypt(encBuf, deskey, ivParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 进行解密后的md5Hash校验
		byte[] md5Hash = null;
		try {
			md5Hash = EncryptUtil.MD5Hash(temp, 16, temp.length - 16);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 进行解密校检
		for (int i = 0; i < md5Hash.length; i++) {
			if (md5Hash[i] != temp[i]) {
				// System.out.println(md5Hash[i] + "MD5校验错误。" + temp[i]);
				throw new Exception("MD5校验错误。");
			}
		}

		// 返回解密后的数组，其中前16位MD5Hash码要除去。
		return new String(temp, 16, temp.length - 16, "utf-8");
	}

	
	public static byte[] TripleDES_CBC_Encrypt(byte[] sourceBuf,
			SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
		byte[] cipherByte;
		// 使用DES对称加密算法的CBC模式加密
		Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");

		encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);

		cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回加密后的字节数组
		return cipherByte;
	}

	
	public static byte[] TripleDES_CBC_Decrypt(byte[] sourceBuf,
			SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {

		byte[] cipherByte;
		// 获得Cipher实例，使用CBC模式。
		Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
		// 初始化加密实例，定义为解密功能，并传入密钥，偏转向量
		decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);

		cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回解密后的字节数组
		return cipherByte;
	}

	
	public static byte[] DES_CBC_Encrypt(byte[] sourceBuf,
			SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
		byte[] cipherByte;
		// 使用DES对称加密算法的CBC模式加密
		Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");

		encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);

		cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回加密后的字节数组
		return cipherByte;
	}

	
	public static byte[] DES_CBC_Decrypt(byte[] sourceBuf,
			SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {

		byte[] cipherByte;
		// 获得Cipher实例，使用CBC模式。
		Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 初始化加密实例，定义为解密功能，并传入密钥，偏转向量
		decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);

		cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回解密后的字节数组
		return cipherByte;
	}
	
	/**
	 * 3des解密
	 */
	public static String decode3Des(String key, String desStr) {
		Base64 base64 = new Base64();
		byte[] keybyte = hex(key);
		byte[] src = base64.decode(desStr);
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

			Cipher c1 = Cipher.getInstance("DESede");
			c1.init(2, deskey);
			return new String(c1.doFinal(src));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	private static byte[] hex(String key) {
		String f = DigestUtils.md5Hex(key);
		byte[] bkeys = new String(f).getBytes();
		byte[] enk = new byte[24];
		for (int i = 0; i < 24; i++) {
			enk[i] = bkeys[i];
		}
		return enk;
	}

	
	public static byte[] MD5Hash(byte[] buf, int offset, int length)
			throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(buf, offset, length);
		return md.digest();
	}

	
	public static String byte2hex(byte[] inStr) {
		String stmp;
		StringBuffer out = new StringBuffer(inStr.length * 2);

		for (int n = 0; n < inStr.length; n++) {
			// 字节做"与"运算，去除高位置字节 11111111
			stmp = Integer.toHexString(inStr[n] & 0xFF);
			if (stmp.length() == 1) {
				// 如果是0至F的单位字符串，则添加0
				out.append("0" + stmp);
			} else {
				out.append(stmp);
			}
		}
		return out.toString();
	}

	public static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {
		int length = bodyByte.length + md5Byte.length;
		byte[] resutlByte = new byte[length];
		
		// 前16位放MD5Hash码
		for (int i = 0; i < length; i++) {
			if (i < md5Byte.length) {
				resutlByte[i] = md5Byte[i];
			} else {
				resutlByte[i] = bodyByte[i - md5Byte.length];
			}
		}

		return resutlByte;
	}

	
	public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {
		// 密钥Base64解密
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = null;
		try {
			buf = decoder.decodeBuffer(encryptKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 前8位为key
		int i;
		for (i = 0; i < key.length; i++) {
			key[i] = buf[i];
		}
		// 后8位为iv向量
		for (i = 0; i < iv.length; i++) {
			iv[i] = buf[i + 8];
		}
	}
	
    
	
	
	 /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
  
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);
    }  
  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
	public static byte[] base64Decode(String base64Code) throws Exception{  
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);  
    }  
  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("utf-8"), "AES"));  
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String encryptbykey(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes("utf-8"), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes,"utf-8");  
    }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String decryptbykey(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    } 
	
	
//	public static void main(String[] args) throws Exception {
//		 String encrypt = encryptbykey("众微", "dG9fd2VjcmVkbw==");  
//         System.out.println("加密后：" + encrypt);  
//         System.out.println(encryptbykey(encrypt, "dG9fd2VjcmVkbw=="));
//         String decrypt = decryptbykey("FQ/vtiKH4k0BGoX7h7lyow==", "dG9fd2VjcmVkbw==");  
//         System.out.println("解密后：" + decrypt+")");  
//	}
	
	/**
     * 使用md5的算法进行加密
    */
    public static String md5utf8(String plainText) {
       byte[] secretBytes = null;
       try {
           secretBytes = MessageDigest.getInstance("md5").digest(
                   plainText.getBytes("utf-8"));
       } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException("没有md5这个算法！");
       }catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码类型！");
		}
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
       // 如果生成数字未满32位，需要前面补0
       for (int i = 0; i < 32 - md5code.length(); i++) {
          md5code = "0" + md5code;
       }
       return md5code;
    }
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
         String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
           md5code = "0" + md5code;
        }
        return md5code;
     }
public static boolean validate(String evidence){
		
		String mac = "";
		String computerName = "";
		if(ServiceUtil.isLinux()){
			try {
				mac = ServiceUtil.getMACAddressByLinux();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				mac = ServiceUtil.getMACAddressByWindows();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			computerName = ServiceUtil.getServiceName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String encrypt = encrypt(mac+computerName+"-"+EVIDENCEDATE);
		if(evidence.equals(md5utf8(encrypt))){
			return true;
		}else{
			return false;
		}
	}
	 // 加密  
    public static String encryptbykey2(String sSrc,String sKey) throws Exception{ 
        String result = "";  
        try {
        	 Cipher cipher;  
             cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
             byte[] raw = sKey.getBytes();  
             SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
             IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
             cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
             byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
             result = new BASE64Encoder().encode(encrypted);  
		} catch (Exception e) {
			e.printStackTrace();
		}
        return URLEncoder.encode(result, "utf-8");
                  
    } 
}
