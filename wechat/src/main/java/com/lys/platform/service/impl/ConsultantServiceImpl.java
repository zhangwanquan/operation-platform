package com.lys.platform.service.impl;

import com.lys.platform.dao.ConsultantMapper;
import com.lys.platform.entity.Consultant;
import com.lys.platform.service.ConsultantService;
import com.lys.platform.vo.ConsultantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 09:58
 * @version: 1.0
 */
@Service
public class ConsultantServiceImpl implements ConsultantService {
    @Autowired
    private ConsultantMapper consultantMapper;
    @Override
    public ConsultantVo getConsultantById(Integer consultantId) {
        Consultant consultant = consultantMapper.selectByPrimaryKey(consultantId);
        if (consultant != null) {
            ConsultantVo consultantVo = new ConsultantVo();
            BeanUtils.copyProperties(consultant, consultantVo);
            return consultantVo;
        }
        return null;
    }
}
