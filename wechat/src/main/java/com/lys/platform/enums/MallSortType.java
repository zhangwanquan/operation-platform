package com.lys.platform.enums;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 16:07
 * @version: 1.0
 */
public enum MallSortType {

    DEFAULT(0, "默认排序"),
    CLICK_COUNT(1, "点击次数");

    private int code;
    private String message;

    MallSortType(int code, String message) {
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
