package com.xiufengd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 阿里图形识别工具类
 */
public class AliImageAnalysis {
	
	
	/**
	 * 身份证识别
	 * @param imgFile  身份证地址
	 * @param side	   正面-face;反面-back
	 * @param appcode 
	 * @return
     * e.g. analysisIdcard("d:\aaa.png", "face", appcode, "服务器地址", "官方提供json")
	 */
	public static JSONObject analysisIdcard(String imgFile, String side, String appcode, String host, String path) {
        Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", side);
        String config_str = configObj.toString();

        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();
        String imgBase64 = imageCompress(imgFile);
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return null;
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if(is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
            }else{
                //System.out.println(res_obj.toJSONString());
            }
            return res_obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	 /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    public static JSONObject analysisLicense(String imgFile, String appcode, String host, String path) {
	    String method = "POST";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    
	    String imgBase64 = imageCompress(imgFile);
	    
        JSONObject obj = new JSONObject();
        obj.put("image", imgBase64);
	    
	    Map<String, String> querys = new HashMap<String, String>();
	    String bodys = obj.toString();

	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	String res = EntityUtils.toString(response.getEntity());
	    	JSONObject res_obj = JSON.parseObject(res);
	    	return res_obj;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
    }
    
    /**
     * 图片压缩
     * @param path
     * @return
     */
    public static String imageCompress(String path) {
    	//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
    	String[] split = path.split("\\.");
    	String type = split[split.length-1];
    	try {
			BufferedImage asBufferedImage = Thumbnails.of(new File(path)).
					scale(1f).outputQuality(0.25f).asBufferedImage();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
	        ImageIO.write(asBufferedImage, type, baos);//写入流中
	        byte[] bytes = baos.toByteArray();//转换成字节
	        BASE64Encoder encoder = new BASE64Encoder();  
	        String png_base64 =  encoder.encodeBuffer(bytes);
	        return png_base64;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
    
    public static Double chineseNumber2Int(String chineseNumber){
    	
    	Pattern pattern = Pattern.compile(".*\\d+.*");
    	if(pattern.matcher(chineseNumber).matches()) {
    		return getNumFromString(chineseNumber);
    	}
        Double result = 0d;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        char[] chArr = new char[]{'拾', '佰', '仟', '万', '亿'};
        
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }
    
    public static Double getNumFromString(String strNum) {
    	Double result = 0d;
        String dest = "";  
        if (strNum != null) {  
            dest = strNum.replaceAll("([^\\d\\.]|\\.(?=[^\\.]*\\.))","");  
        }
        if(StringUtils.isNotBlank(dest)) {
        	result = Double.parseDouble(dest);
        }
        char[] chArr = new char[]{'拾', '佰', '仟', '万', '亿'};
        for(int i = 0; i < strNum.length(); i++) {
        	char c = strNum.charAt(i);
        	for (int j = 0; j < chArr.length; j++) {
                if (c == chArr[j]) {
                    switch (j) {
                    case 0:
                    	result *= 10;
                        break;
                    case 1:
                    	result *= 100;
                        break;
                    case 2:
                    	result *= 1000;
                        break;
                    case 3:
                    	result *= 10000;
                        break;
                    case 4:
                    	result *= 100000000;
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        
        return result;  
    }
	public static void main(String[] args) {
        String appcode = "e8659c8029f44e05be0069d1923c5f55";
        String imgFile = "/Users/wangguoqing/Documents/我的/照片/身份证1.jpeg";
//        JSONObject result = analysisIdcard(imgFile, "face", appcode);//身份证正面-face    反面-back
//        System.out.println(result);
        //{"config_str":"{\"side\":\"face\"}","address":"华盛顿特区宾夕法尼亚大道1600号白宫","nationality":"汉","success":true,"num":"","sex":"","name":"特朗普","birth":"","request_id":"20200214194654_e8836d717ffb01d1fab45201f5d525e3","face_rect":{"size":{"width":87.716903686523438,"height":80.145660400390625},"center":{"x":390.79568481445312,"y":133.10615539550781},"angle":-87.584121704101562},"card_region":[{"x":31,"y":43},{"x":484,"y":43},{"x":484,"y":312},{"x":31,"y":312}],"face_rect_vertices":[{"x":428.984130859375,"y":178.61480712890625},{"x":348.90972900390625,"y":175.2364501953125},{"x":352.60723876953125,"y":87.597503662109375},{"x":432.681640625,"y":90.975860595703125}]}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
