package com.xiufengd.sys.controller;

import com.xiufengd.common.annotation.OperationLogDetail;
import com.xiufengd.common.annotation.OperationType;
import com.xiufengd.common.annotation.OperationUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("sysUser")
@RequestMapping("sysuser")
public class SysUserController {

    @GetMapping("hi")
    @OperationLogDetail(detail = "获取用户名[{{name}}]",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELECT)
    public String syahi(String name){
        return name;
    }

    @GetMapping("hi2")
    public String syahi2(String name){
        return name;
    }
}
