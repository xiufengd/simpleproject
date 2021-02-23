package com.xiufengd.controller.base;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xiufengd.base.annotation.OperationLogDetail;
import com.xiufengd.base.annotation.OperationType;
import com.xiufengd.base.annotation.OperationUnit;
import com.xiufengd.domain.SysUser;
import com.xiufengd.service.SysUserService;
import com.xiufengd.base.redis.RedisUtil;
import com.xiufengd.utils.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("user")
public class SysUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService userService;

    @GetMapping("hi")
    @RequiresPermissions("111")
    @OperationLogDetail(detail = "获取用户名[{{name}}]",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
    public String syahi(String name){
        return name;
    }

    @GetMapping("hi2")
    @RequiresPermissions("222")
    public String syahi2(String name){
        System.out.println("-----------redis存储前----"+redisUtil.get("name"));
        redisUtil.set("name",name);
        System.out.println("-----------redis存储后----"+redisUtil.get("name"));
        return name;
    }

    @GetMapping("getUser")
    public @ResponseBody Object getUser(String id, String name){
//            return userService.getUser(id).toString();
//        if(redisUtil.get("user4")==null){
            SysUser user = userService.getUser(id);
            System.out.println("base:"+user);
            System.out.println("toString:"+user.toString());
//            System.out.println(user.getCreateTime()+"-----"+DateUtil.getStringDate(user.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            redisUtil.set("user4", user.toString());
//        }
        logger.info("ssss{}ssss{}sssee",user,user);
        System.out.println(redisUtil.get("user4").toString());
        return JSONObject.parseObject(redisUtil.get("user4").toString(),SysUser.class);
    }

}