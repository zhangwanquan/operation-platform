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
    @ApiModelProperty(value = "商场id")
    private Integer mallId;
    @ApiModelProperty(value = "商铺名称")
    private String name;
    @ApiModelProperty(value = "业态类型id")
    private Integer type;
    @ApiModelProperty(value = "商铺面积，单位：平米")
    private Integer storeArea;
    @ApiModelProperty(value = "客单价，单位：元")
    private Double unitPrice;
    @ApiModelProperty(value = "租赁状态，0-待租，1-已租")
    private Integer rentalStatus;
    @ApiModelProperty(value = "封面图地址")
    private String coverUrl;
    @ApiModelProperty(value = "店铺所在商场楼层")
    private String floor;
    private Date createTime;
    private Date updateTime;
}
