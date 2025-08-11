package com.lys.platform.vo;

import io.swagger.annotations.ApiModelProperty;
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
    private Integer storeArea;
    private Double unitPrice;
    private Integer rentalStatus;
    private String coverUrl;
    private String floor;
    private Integer businessType;
    private String  businessName;
    private Date createTime;
    private Date updateTime;
}
