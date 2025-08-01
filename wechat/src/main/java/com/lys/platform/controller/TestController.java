package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WeChatService weChatService;

   /* *//**
     * code登录获取用户openid
     * @param code
     * @return
     * @throws Exception
     *//*
    @PostMapping("/login2")
    public GenericResponse login(String code)throws Exception{
        return weChatService.wxLogin(code);
    }*/

    /**
     * 权限测试
     */

    @GetMapping(value = "/test")
    public GenericResponse test(){
        return GenericResponse.response(ServiceError.NORMAL,"test");
    }

    @PostMapping("/test")
    public GenericResponse testPost(){
        return GenericResponse.response(ServiceError.NORMAL,"testPOST");
    }

    @GetMapping("/test/a")
    public GenericResponse testA(){
        return GenericResponse.response(ServiceError.NORMAL,"testManage");
    }

    @GetMapping("/hello")
    public GenericResponse hello(){
        return GenericResponse.response(ServiceError.NORMAL,"hello security");
    }

    @GetMapping("/ddd")
    @PreAuthorize("hasAuthority('ddd:list')")
    public GenericResponse ddd(){
        return GenericResponse.response(ServiceError.NORMAL,"dddList");
    }

    @PostMapping("/ddd")
    @PreAuthorize("hasAuthority('ddd:add')")
    public GenericResponse dddd(){
        return GenericResponse.response(ServiceError.NORMAL,"testPOST");
    }
}
