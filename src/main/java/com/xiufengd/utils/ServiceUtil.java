package com.xiufengd.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class ServiceUtil {
	
	public static void main(String[] a) throws Exception {
        // 判断是Linux还是Windows
        if (isLinux()) {
            // Linux操作系统
            String macAddress = getMACAddressByLinux();
            //System.out.println("Linux macAddress: " + macAddress);
            String Identifier = getIdentifierByLinux();
            //System.out.println("Linux Identifier: " + Identifier);
        } else {
            // Windows操作系统
            String macAddress = getMACAddressByWindows();
            //System.out.println("Windows macAddress: " + macAddress);
            String Identifier = getIdentifierByWindows();
           // System.out.println("Windows Identifier: " + Identifier);
        }
        String serviceName = getServiceName();
        System.out.println(serviceName);
    }
	
	public static Boolean isLinux() {
		String os = System.getProperty("os.name");
		return !os.toLowerCase().startsWith("win");
	}
	
	public static String getMACAddressByLinux() throws Exception {
        String[] cmd = {"ifconfig"};

        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String str1 = sb.toString();
        String str2 = str1.split("ether")[1].trim();
        String result = str2.split("txqueuelen")[0].trim();
        br.close();

        return result;
    }
	
	public static String getIdentifierByLinux() throws Exception {
        String[] cmd = {"fdisk", "-l"};
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String str1 = sb.toString();
        String str2 = str1.split("identifier:")[1].trim();
        String result = str2.split("Device Boot")[0].trim();
        br.close();
        return result;
    }

	public static String getMACAddressByWindows() throws Exception {
        String result = "";
        Process process = Runtime.getRuntime().exec("ipconfig /all");
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

        String line;
        int index = -1;
        while ((line = br.readLine()) != null) {
            index = line.toLowerCase().indexOf("物理地址");
            if (index >= 0) {// 找到了
                index = line.indexOf(":");
                if (index >= 0) {
                    result = line.substring(index + 1).trim();
                }
                break;
            }
        }
        br.close();
        return result;
    }
	
	public static String getIdentifierByWindows() throws Exception {
        String result = "";
        Process process = Runtime.getRuntime().exec("cmd /c dir C:");
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.indexOf("卷的序列号是 ") != -1) {
                result = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
                break;
            }
        }
        br.close();
        return result;
    }
	
	
	public static String getServiceName() throws Exception{
		InetAddress addr = InetAddress.getLocalHost();  
        String hostName=addr.getHostName().toString(); //获取本机计算机名称  
        return hostName;
	}
	
	
}
