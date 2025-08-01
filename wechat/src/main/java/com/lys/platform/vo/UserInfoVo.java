package com.lys.platform.vo;

import lombok.Data;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 16:15
 * @version: 1.0
 */
@Data
public class UserInfoVo {
    private Integer id;
    private String phone;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private Integer authType;
    private Integer approveStatus;
    private ConsultantVo consultant;
}
