package com.xiufengd.controller;

import com.xiufengd.common.annotation.OperationLogDetail;
import com.xiufengd.common.annotation.OperationType;
import com.xiufengd.common.annotation.OperationUnit;
import com.xiufengd.domain.SysUser;
import com.xiufengd.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @GetMapping("hi")
    @OperationLogDetail(detail = "获取用户名[{{name}}]",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
    public String syahi(String name){
        return name;
    }

    @GetMapping("hi2")
    public String syahi2(String name){
        return name;
    }

    @GetMapping("getUser")
    public @ResponseBody SysUser getUser(String id, String name){
        return userService.getUser(id);
    }
}
