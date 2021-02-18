package com.xiufengd.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.*;

@SuppressWarnings("restriction")
public class ZipUtil {

	/**
	 * zip压缩
	 * @param content
	 * @return
	 */
	public static byte[] zipContent(byte[] content){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream zout = new ZipOutputStream(out);
		try {
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(content);
			zout.closeEntry();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * zip解压
	 * @param compressed
	 * @return
	 */
	public static byte[] unZipContent(byte[] compressed){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(compressed);
		ZipInputStream zin = new ZipInputStream(in);
		try {
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer))!= -1) {
			       out.write(buffer, 0, offset);
			}
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				zin.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				in.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * gzip压缩
	 * @param content
	 * @return
	 */
	public static byte[] gzipContent(byte[] content){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gout = null;
		try {
			gout = new GZIPOutputStream(out);
			gout.write(content);
			if(gout!=null)
				try {
					gout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * gzip解压
	 * @param compressed
	 * @return
	 */
	public static byte[] unGzipContent(byte[] compressed){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(compressed);
		GZIPInputStream gzin = null;
		try {
			gzin = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = gzin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			if(gzin!=null)
				try {
					gzin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return  out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * gzip压缩后base64 encode
	 * @param content
	 * @return
	 */
	public static String gzipEncodeBase64(String content){
		byte[] compressed = null;
		try {
			compressed = gzipContent(content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(compressed!=null){
			Base64.encodeBase64String(compressed);
		}
		return "";
	}
	
	/**
	 * base64 decode后 gzip解压
	 * @param content
	 * @return
	 */
	public static String unGzipDeCodeBase64(String content){
		try {
			byte[] compressed = Base64.decodeBase64(content);
			byte[] result = unGzipContent(compressed);
			if(result!=null)
				return new String(result, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getContent(String content){
		try{
			JSONObject result = JSONObject.parseObject(content);
			if(result.containsKey("status")){
				int status = result.getIntValue("status");
				if(status==200){
					String data = result.getString("data");
					if(data!=null&&data.length()>0){
						return unGzipDeCodeBase64(data);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
