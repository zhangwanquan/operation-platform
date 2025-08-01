package com.lys.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/30 08:57
 * @version: 1.0
 */
@Data
public class MallQueryVo {
    @ApiModelProperty(value = "排序规则,0默认排序、1热度排序")
    private Integer sortType;

    @ApiModelProperty(value = "商场名称")
    private String name;

    @ApiModelProperty(value = "城市编码,没有获取到定位传成都的cityCode值")
    @NotBlank(message = "城市编码")
    private String cityCode;


    @ApiModelProperty(value = "商场类型，0-购物中心，1-商业街区，2-百货商场，3-奥特莱斯")
    private Integer type;
    @ApiModelProperty(value = "商场面积，0（0-1万平米），1（1-3万平米），2（3-5万平米），3（5-10万平米），4（10-30万平米）")
    private Integer mallArea;
    @ApiModelProperty(value = "开业状态，0-未开业，1-装修中，2-已开业")
    private Integer status;

    @ApiModelProperty("开业年限，0（0-2年），1（2-5年），2（5-10年），3（10-20年）")
    private Integer businessDuration;
    @ApiModelProperty("商场定位，0-大众商场，1-中端商场，2-高端商场")
    private Integer level;


    @ApiModelProperty("招商面积，0（0-1000平米），1（1000-3000平米），2（3000-5000平米），3（5000-10000平米），4（10000-50000平米），5（5-10万平米），6（10-30万平米）")
    private Integer investmentArea;

    @ApiModelProperty("商铺规模，0（0-100家），1（101-200家），2（201-500家），3（501家以上）")
    private Integer shopScale;
    @ApiModelProperty("业态类型，0（零售），1（餐饮），2（休闲娱乐），3（教育/艺术培训），4（生活服务），5（酒店），6（医疗健康），7（其他类型）")
    private Integer businessType;

    @ApiModelProperty("业态类型名称")
    private String  businessName;

    @ApiModelProperty("停车位数，0（0-500个），1（500-1000个），2（1000-2000个），3（2000-5000个）")
    private Integer parkingSpaceNumber;

    @ApiModelProperty("当前经度")
    private Double longitude;
    @ApiModelProperty("当前纬度")
    private Double latitude;

    @ApiModelProperty("距离范围，0（500米内)，1（1公里内），2（2公里内），3（4公里内），4（8公里内），5（10公里内）")
    private Integer distanceRange;


  /*  private String phone;

    private String address;
    private String coverUrl;


    private Double businessTypeRate;
    private String mainBrandStore;

    private String mallOperator;
    private String mallInvestor;
    private String planUrl;
    private Integer approveStatus;*/










}
