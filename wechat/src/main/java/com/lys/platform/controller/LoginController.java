package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.service.WeChatService;
import com.lys.platform.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/24 11:33
 * @version: 1.0
 */
@RestController
public class LoginController {
    @Autowired
    private WeChatService weChatService;

    /**
     * code登录获取用户openid
     * @param loginVO
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public GenericResponse login(@RequestBody LoginVo loginVO)throws Exception{
        return weChatService.wxLogin(loginVO);
    }

    // 登出
    @PostMapping("/logout")
    public GenericResponse logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 使用 SecurityContextLogoutHandler 来执行注销
        LogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return weChatService.wxLogout(request);
    }

}
