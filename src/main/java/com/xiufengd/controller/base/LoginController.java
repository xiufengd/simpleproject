package com.xiufengd.controller.base;

import com.xiufengd.base.redis.RedisUtil;
import com.xiufengd.utils.IPUtil;
import com.xiufengd.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xiufengd
 * @date 2021/2/22 14:34
 * 未来可期
 */
@RestController
public class LoginController {
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public Object login(HttpServletRequest request,String account,String password){
        if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(password)){
            String ip = IPUtil.getClientIp(request);
            UsernamePasswordToken token = new UsernamePasswordToken(account, password, ip);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            boolean isSuccess = subject.isAuthenticated();
            if (isSuccess) {
                redisUtil.set("name",account);
                return "login success";
            }else{
                return "login fail";
            }
        }
        return "login fail";
    }
    @RequestMapping(value="logout", method = {RequestMethod.POST})
    @ResponseBody
    public Object logout(){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (Exception e) {
            System.out.println(e);
        }

        return "index";
    }
    @RequestMapping(value="index", method = {RequestMethod.POST})
    @ResponseBody
    public Object index(){
        return "index";
    }
}
