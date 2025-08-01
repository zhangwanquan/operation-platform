package com.lys.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/28 13:36
 * @version: 1.0
 */
@Data
public class CertVo {
    private Integer id;
    private Integer certType;
    @NotBlank(message ="联系人姓名" )
    private String userName;
    private String brandName;
    private Integer position;
    private String idCartBack;
    private String idCartFront;
    private String businessLicense;
}
