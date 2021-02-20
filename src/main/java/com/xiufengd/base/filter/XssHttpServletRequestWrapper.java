package com.xiufengd.base.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xiufengd.utils.BCConvert;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return value;
        } else {
            return cleanXSS(value);
        }

    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isEmpty(value)) {
            return value;
        } else {
            return cleanXSS(value);
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = cleanXSS(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String str = getRequestBody(super.getInputStream());
        try {
            Map<String, Object> map = (Map<String, Object>) JSONObject.parseObject(str, Map.class);
            Map<String, Object> resultMap = new HashMap<>();
            for (String key : map.keySet()) {
                Object val = map.get(key);
                if (map.get(key) instanceof String) {
                    resultMap.put(key, cleanXSS(val.toString()));
                } else {
                    resultMap.put(key, val);
                }
            }
            str = JSONObject.toJSONString(resultMap).toString();
        } catch (JSONException e) {
            if (str.startsWith("[")) {
                JSONArray jsonArr = JSONArray.parseArray(str);
                if (jsonArr != null) {
                    int length = jsonArr.size();
                    String[] escapseValues = new String[length];
                    for (int i = 0; i < length; i++) {
                        escapseValues[i] = cleanXSS(jsonArr.get(i).toString());
                    }
                    str = JSONArray.toJSONString(escapseValues).toString();
                }

            }
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes("utf-8"));

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    private String getRequestBody(InputStream stream) {
        String line = "";
        StringBuilder body = new StringBuilder();
        int counter = 0;

        // 读取POST提交的数据内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        try {
            while ((line = reader.readLine()) != null) {

                body.append(line);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body.toString();
    }

    private String cleanXSS(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        } else {
            if (value != null) {
                if (value != null) {
                    // Avoid null characters
                    value = value.replaceAll("", "");
                    // Avoid anything between script tags
                    Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
                    // 会误伤百度富文本编辑器
//                    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//                    value = scriptPattern.matcher(value).replaceAll("");
//                    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//                    value = scriptPattern.matcher(value).replaceAll("");
                    // Remove any lonesome </script> tag
                    scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Remove any lonesome <script ...> tag
                    scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid eval(...) e­xpressions
                    scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid e­xpression(...) e­xpressions
                    scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid javascript:... e­xpressions
                    scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid vbscript:... e­xpressions
                    scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid onload= e­xpressions
                    scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    value = scriptPattern.matcher(value).replaceAll("");
                    // Avoid alert
                    scriptPattern = Pattern.compile("alert\\((.*?)\\)", Pattern.CASE_INSENSITIVE);
                    value = scriptPattern.matcher(value).replaceAll("");
                }
            }

            String uri = getRequestURI();
            if (uri != null) {
//            if (uri != null && ignoreToSBCUri.indexOf(uri) == -1) {
                value = BCConvert.ToSBC(value);
            }
            return value;
        }
    }
}
