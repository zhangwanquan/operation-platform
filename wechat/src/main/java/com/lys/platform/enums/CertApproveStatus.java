package com.lys.platform.enums;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 16:07
 * @version: 1.0
 */
public enum CertApproveStatus {

    UNSUBMITTED(0, "未提交"),
    UNREVIEWED(1, "未审核"),
    REVIEWED(2, "已审核"),
    REJECTED(3, "已拒绝");

    private int code;
    private String message;

    CertApproveStatus(int code, String message) {
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
