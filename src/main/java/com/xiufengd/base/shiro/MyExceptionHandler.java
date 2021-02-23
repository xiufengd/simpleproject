package com.xiufengd.base.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiufengd
 * @date 2021/2/22 14:27
 * 未来可期
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
//        log.error("没有通过权限验证！", e);
        System.out.println("无权限");
        return "没有通过权限验证！";
    }
}
