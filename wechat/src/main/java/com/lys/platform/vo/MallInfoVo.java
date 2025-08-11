package com.lys.platform.vo;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "商场账号（联系人手机号）")
    private String phone;
    @ApiModelProperty(value = "商场名称")
    private String name;
    @ApiModelProperty(value = "商场类型，0-购物中心，1-商业街区，2-百货商场，3-奥特莱斯")
    private Integer type;
    @ApiModelProperty(value = "城市编码")
    private String cityCode;
    @ApiModelProperty(value = "城市名称")
    private String cityName;
    @ApiModelProperty(value = "开业状态，0-未开业，1-装修中，2-已开业")
    private Integer status;
    @ApiModelProperty(value = "商场定位，0-大众商场，1-中端商场，2-高端商场")
    private Integer level;
    @ApiModelProperty(value = "商场定位 经度")
    private Double longitude;
    @ApiModelProperty(value = "商场定位 纬度")
    private Double latitude;
    @ApiModelProperty(value = "商场地址")
    private String address;
    @ApiModelProperty(value = "商场封面图/视频")
    private String coverUrl;
    @ApiModelProperty(value = "开业时间")
    private Date openTime;
    @ApiModelProperty(value = "招商面积，0（0-1000平米），1（1000-3000平米），2（3000-5000平米），3（5000-10000平米），4（10000-50000平米），5（5-10万平米），6（10-30万平米）")
    private Integer investmentArea;
    @ApiModelProperty(value = "品牌主力店")
    private String mainBrandStore;
    @ApiModelProperty(value = "商铺规模，0（0-100家），1（101-200家），2（201-500家），3（501家以上）")
    private Integer shopScale;
    @ApiModelProperty(value = "商场面积，单位：平方米")
    private Integer mallArea;
    @ApiModelProperty(value = "停车位数")
    private Integer parkingSpaceNumber;
    @ApiModelProperty(value = "物业费，单位：元/月/平")
    private Double propertyFee;
    @ApiModelProperty(value = "商场运营商")
    private String mallOperator;
    @ApiModelProperty(value = "商场投资商")
    private String mallInvestor;
    @ApiModelProperty(value = "商场特色业态类型，用|分割")
    private String features;
    @ApiModelProperty(value = "平面图地址")
    private String planUrl;
    @ApiModelProperty(value = "审核状态，0-未审核，1-已审核，2-已拒绝")
    private Integer approveStatus;
    @ApiModelProperty(value = "商场评分")
    private Double score;
    private Date createTime;
    private Date updateTime;









}
