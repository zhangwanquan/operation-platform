package com.lys.platform.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 11:18
 * @version: 1.0
 */
@Data
public class StoreInfoVo {
    private Integer id;
    private Integer mallId;
    private String name;
    private Integer type;
    private Integer store_area;
    private Double unitPrice;
    private Integer rentalStatus;
    private String cover_url;
    private String floor;
    private Date createTime;
    private Date updateTime;
}
