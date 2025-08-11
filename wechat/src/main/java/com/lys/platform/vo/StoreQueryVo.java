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
public class StoreQueryVo {

    @ApiModelProperty(value = "商场id")
    @NotBlank(message = "商场id")
    private Integer mallId;

    @ApiModelProperty(value = "排序规则,0默认排序、1按单价从高到低、2按单价从低到高3、按面积从高到低4、按面积从低到高")
    private Integer sortType;

    @ApiModelProperty(value = "商铺名称")
    private String name;

    @ApiModelProperty(value = "租凭状态")
    private Integer rentalStatus;

    @ApiModelProperty("业态类型，0（零售），1（餐饮），2（休闲娱乐），3（教育/艺术培训），4（生活服务），5（酒店），6（医疗健康），7（其他类型）")
    private Integer businessType;

    @ApiModelProperty("业态类型名称")
    private String  businessName;






}
