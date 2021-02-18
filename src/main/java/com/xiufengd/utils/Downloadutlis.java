package com.xiufengd.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class Downloadutlis   {

    /**
     *
     * @Description:文件下载 downloadFile(HttpServletResponse,"aaaa.txt","d://aaaa.txt")
     * @Author:xiufengd
     * @Date:  2021/2/18 16:13
     * @Param: response
     * @Param: fileName
     * @Param: path
     * @return
     * @Throws
     */
    public static void downloadFile(HttpServletResponse response, String fileName, String path){

        if (fileName != null) {
            //设置文件路径
            File file = new File(path);
            if (file.exists()) {
                try {
                    response.reset(); // 非常重要
                    response.setContentType("application/x-msdownload");
                    response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"),"ISO-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
