package com.lys.platform.handler;

import com.alibaba.fastjson.JSON;
import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.service.WeChatService;
import com.lys.platform.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: jamesluozhiwei
 * @description: 登出成功
 */
@Component
@Slf4j
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WeChatService weChatService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //没有logout不走这里、若使用security的formLogin则自己添加业务实现（移除token等等）
        weChatService.wxLogout(request);
        response.getWriter().write(JSON.toJSONString(GenericResponse.response(ServiceError.NORMAL)));
    }

}