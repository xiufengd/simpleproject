package com.xiufengd.simpleproject.aoplogs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class ClassAsect {

    @Before("execution(* com.xiufengd.simpleproject.sys.controller.*.* (..))")
    public void before(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截,"+method.getName());
    }
}
