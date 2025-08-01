package com.lys.platform.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 16:25
 * @version: 1.0
 */
@Data
public class LoginVo {
    private String code;
    @JsonProperty("encrypted_data")
    private String encryptedData;
    private String iv;
//    private UserInfoVo userInfo;
}
