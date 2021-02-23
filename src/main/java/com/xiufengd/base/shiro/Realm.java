package com.xiufengd.base.shiro;

import com.alibaba.fastjson.JSONObject;
import com.xiufengd.domain.SysUser;
import com.xiufengd.utils.RedisUtil;
import com.xiufengd.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class Realm extends AuthorizingRealm {

	@Resource
	private com.xiufengd.base.redis.RedisUtil redisUtil;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("1");
        if("1".equals(redisUtil.get("name"))){
			info.addStringPermission("111");
		}else{
			info.addStringPermission("222");
		}
//		info.addStringPermission("222");
//        String userId = WebUtil.getCurrentUser();
//        Map<String, Object> map = userService.usercertipermission(userId);
//        List<String> list = authorizeService.queryPermissionByUserId(userId);
//        for (String permission : list) {
//            if (StringUtils.isNotBlank(permission)) {
//                // 添加基于Permission的权限信息
//                info.addStringPermission(permission);
//            }
//        }
//        if(map!=null&&map.get("iscreti").toString()!=null){
//        	if(map.get("iscreti").equals("3")){
//        		info.addStringPermission("已认证");
//        	}
//        }
////        if(map!=null&&!map.get("jzuser").toString().equals("1")){
////        	info.addStringPermission("已完善");
////        }
//        // 添加用户权限
//        info.addStringPermission("user");
        return info;
    }

    // 登录验证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
    	AuthenticationInfo authcInfo = null;
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = new String(token.getPassword());
//		if ("1".equals(username)&&"1".equals(password)){
			authcInfo = new SimpleAuthenticationInfo(username, password,"1111");
//		}
		return authcInfo;
    }

    /** 保存session */
    private void saveSession(String account) {
//    	 Subject currentUser = SecurityUtils.getSubject();
//         Session session = currentUser.getSession();
//         String sessionId = session.getId().toString();
//         Serializable serializable = RedisUtil.get(account);
//         if(serializable==null||serializable.equals("")){
//        	 RedisUtil.set(account, sessionId);
//         }else{
//        	 sessionRepository.delete(serializable.toString());
//			 sessionRepository.cleanupExpiredSessions();
//			 RedisUtil.set(account, sessionId);
//         }
    	
    }

}
