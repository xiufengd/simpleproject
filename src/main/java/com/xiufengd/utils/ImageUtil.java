package com.xiufengd.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;


public abstract class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    
    /**
     * 图片压缩
     * @param from
     * @param to
     * @return
     */
    public static void imageCompress(MultipartFile from, File to) {
    	imageCompress(from, to,1f);
    }
    
    /**
     * 图片压缩
     * @param from
     * @param to
     * @return
     */
    public static void imageCompressBySize(MultipartFile from, File to) {
    	if((double)from.getSize()/1048576>=2) {
    		//如果图片大小超过2M，按0.25比例压缩，否则按o.5
    		imageCompress(from, to,0.25f);
    	}else {
    		imageCompress(from, to,0.5f);
    	}
    	
    }
    /**
     * 图片压缩
     * @param from
     * @param to
     * @param outputQuality
     * @return
     */
    public static void imageCompress(MultipartFile from, File to, float outputQuality) {
        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        try {
        	Thumbnails.of(from.getInputStream()). scale(1f).outputQuality(outputQuality).toFile(to);
        } catch (IOException e) {
            logger.error("图片压缩失败:{}",e);
            try {
                from.transferTo(to);
            } catch (IOException ex) {
                logger.error("文件转储失败:{}",e);
            }
        }
    }

    public static void imageCompressDynamic(MultipartFile from, File to) {
        try {
            byte[] inBytes = IOUtils.toByteArray(from.getInputStream());

            byte[] outBytes = compressPicCycle(inBytes, 1, 0.89);

            FileOutputStream fos = new FileOutputStream(to);
            IOUtils.write(outBytes, fos);
        } catch (IOException e) {
            logger.error("图片压缩失败:{}",e);
            try {
                from.transferTo(to);
            } catch (IOException ex) {
                logger.error("文件转储失败:{}",e);
            }
        }
    }

    /**
     *
     * @param bytes 原图片字节数组
     * @param desFileSize 指定图片大小,单位 MB
     * @param accuracy 精度,递归压缩的比率,建议小于0.9
     * @return
     */
    public static byte[] compressPicCycle(byte[] bytes, long desFileSize, double accuracy) throws IOException{
        long fileSize = bytes.length;
        logger.debug("compressPicCycle =====fileSize======== {}", fileSize);
        // 判断图片大小是否小于指定图片大小
        if(fileSize <= desFileSize * 1024* 1024){
            return bytes;
        }
        //计算宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        int imgWidth = bim.getWidth();
        logger.debug(imgWidth+"====imgWidth=====");
        int imgHeight = bim.getHeight();
        int desWidth = new BigDecimal(imgWidth).multiply( new BigDecimal(accuracy)).intValue();
        logger.debug(desWidth+"====desWidth=====");
        int desHeight = new BigDecimal(imgHeight).multiply( new BigDecimal(accuracy)).intValue();
        //字节输出流（写入到内存）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(bytes)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
        //如果不满足要求,递归直至满足要求
        return compressPicCycle(baos.toByteArray(), desFileSize, accuracy);
    }

    /**
     * 判断文件大小
     *
     * @param len
     *            文件长度
     * @param size
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean greater(Long len, int size, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return true;
        }
        return false;
    }

    /**
     * 将base64流保存到本地
     * @param imgStr
     * @param imgFilePath
     * @return
     */
    @SuppressWarnings("restriction")
    public static boolean Base64ToImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
        // 图像数据为空
        if (isEmpty(imgStr)) {
            return false;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判空
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        return input == null || input.equals("");
    }

    //测试入口
    public static void imageCompressDynamic(String source, String dest) {
        try {
            InputStream inputStream = new FileInputStream(source);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }

            byte[] outBytes = compressPicCycle(output.toByteArray(), 1, 0.89);

            InputStream in = new ByteArrayInputStream(outBytes);
            File file = new File(dest);
            FileOutputStream fos = new FileOutputStream(file);
            while (-1 != (n = in.read(buffer))) {
                fos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            logger.error("imageCompressDynamic failed", e);
        }
    }

    public static void main(String[] args) {
        imageCompressDynamic("/Users/wangguoqing/Documents/我的/我们/aa.JPG", "/Users/wangguoqing/Documents/我的/我们/bb.JPG");
    }
}
