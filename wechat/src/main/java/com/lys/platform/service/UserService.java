package com.lys.platform.service;

import com.lys.platform.entity.WxUserInfo;
import com.lys.platform.vo.UserInfoVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 15:57
 * @version: 1.0
 */
public interface UserService {
    WxUserInfo getUserInfoByOpenid(String openid);

    WxUserInfo creatUserInfo(WxUserInfo userInfo);

    void updateUserInfo(UserInfoVo userInfoVo);

    UserInfoVo getUserInfoFromToken(HttpServletRequest request);

    Integer getUserIdFromToken(HttpServletRequest request);

    String getPhoneFromToken(HttpServletRequest request);

    Integer getCustomerIdFromToken(HttpServletRequest request);
}
