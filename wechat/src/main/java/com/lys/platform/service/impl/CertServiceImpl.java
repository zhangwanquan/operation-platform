package com.lys.platform.service.impl;

import com.lys.platform.dao.CertMapper;
import com.lys.platform.entity.CertInfo;
import com.lys.platform.enums.CertApproveStatus;
import com.lys.platform.service.CertService;
import com.lys.platform.service.CustomerService;
import com.lys.platform.vo.CertVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/28 11:14
 * @version: 1.0
 */
@Service
public class CertServiceImpl implements CertService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CertMapper certMapper;
    @Override
    public CertVo getCertInfoByPhone(String phone) {
        CertVo certVo = null;
        CertInfo certInfo = certMapper.selectOne(new CertInfo().setPhone(phone));
        if (certInfo != null) {
            certVo = new CertVo();
            BeanUtils.copyProperties(certInfo, certVo);
        }
        return certVo;
    }

    @Override
    @Transactional
    public void saveOrUpdateCertInfo(String phone, CertVo certVo) {
        CertInfo certInfo = certMapper.selectOne(new CertInfo().setPhone(phone));
        if (certInfo == null) {
            certInfo = new CertInfo();
            certInfo.setPhone(phone);
            BeanUtils.copyProperties(certVo, certInfo);
            certMapper.insertSelective(certInfo);
            customerService.updateCustomerApproveStatus(phone, CertApproveStatus.UNREVIEWED.code());
        } else {
            if (!certInfo.getId().equals(certVo.getId())) {
                throw new RuntimeException("只能修改自己的认证信息");
            }
            certInfo.setUserName(certVo.getUserName());
            certInfo.setBrandName(certVo.getBrandName());
            certInfo.setCertType(certVo.getCertType());
            certInfo.setIdCartFront(certVo.getIdCartFront());
            certInfo.setIdCartBack(certVo.getIdCartBack());
            certInfo.setBusinessLicense(certVo.getBusinessLicense());
            // 编辑后审核状态变为“未审核”
//            certInfo.setStatus(CertStatus.UNREVIEWED.code());
            certMapper.updateByPrimaryKey(certInfo);
            // 更新customer表的审核状态
            customerService.updateCustomerApproveStatus(phone, CertApproveStatus.UNREVIEWED.code());
        }
    }

}
