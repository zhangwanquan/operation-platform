package com.lys.platform.dao;

import com.lys.platform.entity.CertInfo;
import com.lys.platform.entity.WxUserInfo;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 12:31
 * @version: 1.0
 */
public interface CertMapper extends Mapper<CertInfo>, MySqlMapper<CertInfo> {
}
