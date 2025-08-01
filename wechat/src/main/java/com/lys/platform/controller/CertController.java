package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.service.CertService;
import com.lys.platform.service.UserService;
import com.lys.platform.vo.CertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * 认证相关类
 * @Author jiangzhili
 * @Date 2025/7/24 11:21
 * @version: 1.0
 */
@RestController
@RequestMapping("/cert")
public class CertController {
    @Autowired
    private CertService certService;
    @Autowired
    private UserService userService;
    // 发起认证
    @PostMapping("/create")
    public GenericResponse createCertInfo(HttpServletRequest request, @RequestBody @Validated CertVo certVo) {
        String phone = userService.getPhoneFromToken(request);
        certService.saveOrUpdateCertInfo(phone, certVo);
        return GenericResponse.response(ServiceError.NORMAL);
    }

    // 认证详情
    @GetMapping("/detail")
    public GenericResponse getCertInfo(HttpServletRequest request) {
        String phone = userService.getPhoneFromToken(request);
        // 获取自己的认证详情
        CertVo certVo = certService.getCertInfoByPhone(phone);
        return GenericResponse.response(ServiceError.NORMAL, certVo);
    }

    // 编辑认证
    @PostMapping("/update")
    public GenericResponse updateCertInfo(HttpServletRequest request,@RequestBody @Validated CertVo certVo) {
        String phone = userService.getPhoneFromToken(request);
        certService.saveOrUpdateCertInfo(phone, certVo);
        return GenericResponse.response(ServiceError.NORMAL);
    }
}
