package com.xiufengd.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: liyang
 * @date：2018/11/21 0021
 * describe：
 */
public class SessionThreadLocal {

    private static Logger logger = LoggerFactory.getLogger(SessionThreadLocal.class);

    private static ThreadLocal<String> openId;
    private static ThreadLocal<Boolean> existUser;


    static {
        openId = ThreadLocal.withInitial(() -> "");
        existUser = ThreadLocal.withInitial(() -> false);
    }

    public static void setOpenId(String str){
        openId.set(str);
    }

    public static String getOpenId(){
        return openId.get();
    }

    public static void remove(){
        openId.set("");
        existUser.set(false);
    }

    public static void setExistUser(Boolean exist) {
        existUser.set(exist);
    }

    public static Boolean getExistUser(){
        return existUser.get();
    }
}
