package com.lys.platform.service;

import com.lys.platform.vo.CertVo;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/28 11:13
 * @version: 1.0
 */
public interface CertService {
    CertVo getCertInfoByPhone(String phone);

    void saveOrUpdateCertInfo(String phone, CertVo certInfo);

}
