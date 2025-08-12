package com.lys.platform.enums;

/**
 * @Description:距离范围，0（500米内)，1（1公里内），2（2公里内），3（4公里内），4（8公里内），5（10公里内）
 * @Author jiangzhili
 * @Date 2025/7/26 16:07
 * @version: 1.0
 */
public enum DistanceRangeType {

    RANGE_500_M(0, 0.5 ,"500米内"),
    RANGE_1_KM(1,  1,"1公里内"),
    RANGE_2_KM(2, 2, "2公里内"),
    RANGE_4_KM(3, 4, "4公里内"),
    RANGE_8_KM(4, 8, "8里内"),
    RANGE_10_KM(5, 10,"10公里内");

    private int code;
    private double range;
    private String message;

    DistanceRangeType(int code, double range, String message) {
        this.code = code;
        this.range = range;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public double range() {
        return this.range;
    }


    public String message() {
        return this.message;
    }

    public static DistanceRangeType of(Integer code) {
        for (DistanceRangeType temp : DistanceRangeType.values()) {
            if (code.equals(temp.code) ) {
                return temp;
            }
        }
        return null;
    }
}
