package com.lys.platform.dao;

import com.lys.platform.entity.MallBusinessTypeExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 12:31
 * @version: 1.0
 */
public interface MallBusinessTypeExtMapper extends Mapper<MallBusinessTypeExt>, MySqlMapper<MallBusinessTypeExt> {
    List<MallBusinessTypeExt> selectByMallIdAndBusinessTypeIds(@Param("mallId") Integer mallId, @Param("businessTypeIds")List<Integer> businessTypeConfigIds);
}
