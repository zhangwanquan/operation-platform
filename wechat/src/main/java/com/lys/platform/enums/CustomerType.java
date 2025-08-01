package com.lys.platform.enums;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 16:07
 * @version: 1.0
 */
public enum CustomerType {

    BRAND(0, "品牌客户"),
    MALL(1, "商场客户");

    private int code;
    private String message;

    CustomerType(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int code() {
        return this.code;
    }


    public String message() {
        return this.message;
    }
}
