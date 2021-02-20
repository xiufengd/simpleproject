package com.xiufengd.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xiufengd.base.annotation.OperationLogDetail;
import com.xiufengd.base.annotation.OperationType;
import com.xiufengd.base.annotation.OperationUnit;
import com.xiufengd.domain.SysUser;
import com.xiufengd.service.SysUserService;
import com.xiufengd.base.redis.RedisUtil;
import com.xiufengd.utils.DateUtil;
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

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService userService;

    @GetMapping("hi")
    @OperationLogDetail(detail = "获取用户名[{{name}}]",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
    public String syahi(String name){
        return name;
    }

    @GetMapping("hi2")
    public String syahi2(String name){
        System.out.println("-----------redis存储前----"+redisUtil.get("name"));
        redisUtil.set("name",name);
        System.out.println("-----------redis存储后----"+redisUtil.get("name"));
        return name;
    }

    @GetMapping("getUser")
    public @ResponseBody SysUser getUser(String id, String name){
//        if(redisUtil.get("user4")==null){
            SysUser user = userService.getUser(id);
            System.out.println(user.getCreateTime()+"-----"+DateUtil.getStringDate(user.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            redisUtil.set("user4", user.toString());
//        }
        System.out.println(redisUtil.get("user4").toString());
        return JSONObject.parseObject(redisUtil.get("user4").toString(),SysUser.class);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getStringDate(DateUtil.getDateFromString("2020-09-10 17:11:37","yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
    }
}