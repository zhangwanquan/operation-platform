package com.lys.platform.service;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信业务接口
 */
public interface WeChatService {

    /**
     * 小程序登录
     * @param loginVO
     * @return
     */
    GenericResponse wxLogin(LoginVo loginVO)throws Exception;

    GenericResponse wxLogout(HttpServletRequest request);
}
