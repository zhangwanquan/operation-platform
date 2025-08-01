package com.lys.platform.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/30 08:57
 * @version: 1.0
 */
@Data
public class MallInfoVo {
    private Integer id;
    private String phone;
    private String name;
    private Integer type;
    private Integer cityCode;
    private String cityName;
    private Integer status;
    private Integer level;

    private Double longitude;
    private Double latitude;
    private String address;
    private String coverUrl;

    private Integer businessDuration;
    private Integer investmentArea;
    private Integer businessType;
    private String businessTypeName;


    private Double businessTypeRate;

    private String mainBrandStore;

    private Integer shopScale;
    private Integer mallArea;
    private Integer parkingSpaceNumber;

    private String mallOperator;
    private String mallInvestor;
    private String planUrl;
    private Integer approveStatus;

    private Date createTime;
    private Date updateTime;









}
