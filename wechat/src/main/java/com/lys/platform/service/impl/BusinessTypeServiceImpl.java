package com.lys.platform.service.impl;

import com.lys.platform.dao.BusinessTypeConfigMapper;
import com.lys.platform.entity.BusinessTypeConfig;
import com.lys.platform.service.BusinessTypeService;
import com.lys.platform.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/8/12 10:02
 * @version: 1.0
 */
public class BusinessTypeServiceImpl implements BusinessTypeService {
    @Autowired
    private BusinessTypeConfigMapper businessTypeConfigMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<BusinessTypeConfig> getAllBusinessTypeConfigFromCache() {
        // 查询一共有哪些业态类型
        List<BusinessTypeConfig> businessTypeConfigs = (List<BusinessTypeConfig>) redisUtil.get(RedisUtil.BUSINESS_TYPE_CONFIG_KEY);

        if (CollectionUtils.isEmpty(businessTypeConfigs)) {
            // 从数据库查询,并存入缓存中
            List<BusinessTypeConfig> businessTypeConfigsFromDb = businessTypeConfigMapper.selectAll();
            redisUtil.setList(RedisUtil.BUSINESS_TYPE_CONFIG_KEY, businessTypeConfigsFromDb);
            businessTypeConfigs = businessTypeConfigsFromDb;
        }
        return businessTypeConfigs;

    }

}
