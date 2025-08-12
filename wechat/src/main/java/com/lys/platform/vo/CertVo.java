package com.lys.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/28 13:36
 * @version: 1.0
 */
@Data
public class CertVo {
    private Integer id;
    @ApiModelProperty(value = "认证类型0、未认证1、个人认证2、品牌认证")
    private Integer certType;
    @NotBlank(message ="联系人姓名" )
    private String userName;
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    @ApiModelProperty(value = "职位.枚举值还没有给到")
    private Integer position;
    @ApiModelProperty(value = "身份证背面")
    private String idCartBack;
    @ApiModelProperty(value = "身份证正面")
    private String idCartFront;
    @ApiModelProperty(value = "营业职照")
    private String businessLicense;
    @ApiModelProperty(value = "城市编码")
    private Integer cityCode;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "行政区域码")
    private Integer regionCode;
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;
    @ApiModelProperty(value = "县编码")
    private Integer countyCode;
    @ApiModelProperty(value = "县名称")
    private String countyName;
    @ApiModelProperty(value = "业态类型")
    private Integer shopBusinessType;
    @ApiModelProperty(value = "拟开店面积")
    private Integer siteSelectionArea;
    @ApiModelProperty(value = "已开店面积")
    private Integer openedStoreArea;
    @ApiModelProperty(value = "已开店数量")
    private Integer openedStoreCount;




}
