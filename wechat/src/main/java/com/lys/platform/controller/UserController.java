package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.entity.WxUserInfo;
import com.lys.platform.service.UserService;
import com.lys.platform.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/24 11:25
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // 保存用户信息
    @PostMapping("/update")
    public GenericResponse updateUserInfo(UserInfoVo userInfoVo) {
        userService.updateUserInfo(userInfoVo);
        return GenericResponse.response(ServiceError.NORMAL);
    }


    //
    @GetMapping("/getUser")
    @ResponseBody
    public GenericResponse getUserInfo(HttpServletRequest request) {
        UserInfoVo userInfo = userService.getUserInfoFromToken(request);
        return GenericResponse.response(ServiceError.NORMAL, userInfo);
    }


    // 获取用户基本信息,电话号码,是否认证等


    // 我的收藏


    // 我的认证资料
}
