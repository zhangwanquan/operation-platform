package com.lys.platform.service;

import com.lys.platform.entity.BusinessTypeConfig;

import java.util.List;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/8/12 10:01
 * @version: 1.0
 */
public interface BusinessTypeService {
    public List<BusinessTypeConfig> getAllBusinessTypeConfigFromCache();
}
