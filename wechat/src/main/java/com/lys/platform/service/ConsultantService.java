package com.lys.platform.service;

import com.lys.platform.entity.Customer;
import com.lys.platform.vo.ConsultantVo;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 08:57
 * @version: 1.0
 */
public interface ConsultantService {

    ConsultantVo getConsultantById(Integer consultantId);
}
