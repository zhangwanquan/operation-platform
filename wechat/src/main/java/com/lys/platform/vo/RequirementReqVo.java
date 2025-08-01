package com.lys.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 10:24
 * @version: 1.0
 */
@Data
public class RequirementReqVo {
    private String cityCode;
    private String cityName;
    private String regionCode;
    private String regionName;
    private String countyCode;
    private String countyName;
    private Integer brandType;
    private String brandUnitPrice;
    private Integer storeNumber;
    private Integer operationMode;
    private Integer siteSelectionTime;
    private Integer businessType;
    private Integer siteSelectionArea;
    private Integer rentBudget;
    private String contact_phone;
    private String remark;

    @JsonIgnore
    private Integer customerId;


//    private Integer contact_flg;

}
