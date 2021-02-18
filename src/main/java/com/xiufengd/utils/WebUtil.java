package com.xiufengd.utils;

import com.xiufengd.common.OperationEnv;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Web层辅助类
 * 
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:19:28
 */
public final class WebUtil {
	private WebUtil() {
	}

	/**
	 * 获取指定Cookie的值
	 * 
	 * @param cookieName cookie名字
	 * @param defaultValue 缺省值
	 * @return
	 */
	public static final String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		Cookie cookie = WebUtils.getCookie(request, cookieName);
		if (cookie == null) {
			return defaultValue;
		}
		return cookie.getValue();
	}

	/** 保存当前用户 */
	public static final void saveCurrentUser(Object user) {
		setSession(Constants.CURRENT_USER, user);
	}

	/** 获取当前用户 */
	public static final String getCurrentUser() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				return (String) session.getAttribute(Constants.CURRENT_USER);
			}
		}
		return null;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 */
	public static final void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/** 移除当前用户 */
	public static final void removeCurrentUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.CURRENT_USER);
	}

	/**
	 * 获得国际化信息
	 * 
	 * @param key 键
	 * @param request
	 * @return
	 */
	public static final String getApplicationResource(String key, HttpServletRequest request) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources", request.getLocale());
		return resourceBundle.getString(key);
	}

	/**
	 * 获得参数Map
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getParameterMap(HttpServletRequest request) {
		return WebUtils.getParametersStartingWith(request, null);
	}
	
	/**
	 * 获取操作环境
	 * 
	 * @param request
	 * @return
	 */
	public static final String getOperationEnv(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if (userAgent.indexOf("micromessenger") != -1) {
			return OperationEnv.H5.toString();
		} else if (userAgent.indexOf("windows") != -1) {
			return OperationEnv.PC.toString();
		} else {
			return OperationEnv.MOBILE.toString();
		}
	}

//	/** 获取客户端IP */
//	public static final String getHost(HttpServletRequest request) {
//		String ip = request.getHeader("X-Forwarded-For");
//		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)|| "127.0.0.1".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)|| "127.0.0.1".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)|| "127.0.0.1".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Real-IP");
//		}
//		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)|| "127.0.0.1".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		if ("127.0.0.1".equals(ip)) {
//			InetAddress inet = null;
//			try { // 根据网卡取本机配置的IP
//				inet = InetAddress.getLocalHost();
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			}
//			ip = inet.getHostAddress();
//		}
//		if (ip == null || ip.length() == 0 || ip.indexOf(":") > -1) {
//			try {
//				ip = InetAddress.getLocalHost().getHostAddress();
//			} catch (UnknownHostException e) {
//				ip = null;
//			}
//		}
//		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//		if (ip != null && ip.length() > 15) {
//			if (ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
//		return ip;
//	}
}
