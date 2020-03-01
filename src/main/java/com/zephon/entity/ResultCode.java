package com.zephon.entity;

/**
 * @author Zephon
 * @version V1.0
 * @Package com.zephon.common.entity
 * @date 2020/2/28 下午1:44
 * @Copyright ©
 */

public enum  ResultCode {
    /**
     * 成功
     */
    SUCCESS(true,10000,"操作成功！"),
    /**
     * 失败
     */
    FAIL(false,10001,"操作失败"),
    /**
     * 未认证
     */
    UNAUTHENTICATED(false, 10002, "您还未登录"),
    /**
     * 权限不足
     */
    UNAUTHORISE(false, 10003, "权限不足"),
    /**
     * 系统繁忙
     */
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),

    /**
     * 用户操作返回码：20001
     */
    MOBILEORPASSWORDERROR(false,20001,"用户名或密码错误");

    boolean success;
    Integer code;
    String message;
    ResultCode(boolean success,Integer code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public boolean success(){
        return success;
    }
    public int code(){
        return code;
    }
    public String message(){
        return message;
    }
}
