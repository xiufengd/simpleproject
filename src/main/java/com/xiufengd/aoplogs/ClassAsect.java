package com.xiufengd.aoplogs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ClassAsect {

    @Before("execution(* com.xiufengd.controller.*.* (..))" +
            "||execution(* com.xiufengd.service.*.* (..))")
    public void before(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        System.out.println("调用方法："+ point.getSignature().getDeclaringTypeName()+":"+method.getName()+";参数为："+getParams(((MethodSignature)point.getSignature()).getParameterNames(),point.getArgs()));
    }
    public String getParams(String[] argNames, Object[] args){
        Map<Object, Object> map = new HashMap<>(4);
        for(int i = 0;i < argNames.length;i++){
            map.put(argNames[i],args[i]);
        }
        return map.toString();
    }
}
