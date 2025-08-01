package com.lys.platform.common;

public enum ServiceError {

    NORMAL(200, "操作成功"),
    UN_KNOW_ERROR(-1, "未知错误"),

    /** Global Error */
    GLOBAL_ERR_NO_SIGN_IN(-10001,"未登录或登录过期/Not sign in"),
    GLOBAL_ERR_NO_CODE(-10002,"code错误/error code"),
    GLOBAL_ERR_NO_AUTHORITY(-10003, "没有操作权限/No operating rights"),


    GLOBAL_ERR_NO_CERT(-10011,"没有认证信息,或认证不通过"),
    GLOBAL_ERR_CERTING(-10012,"认证中/error code"),
    GLOBAL_ERR_REQUIREMENT_REPEATED(-10013,"请求重复提交"),


    ;


    private int code;
    private String msg;

    private ServiceError(int code, String msg)
    {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
