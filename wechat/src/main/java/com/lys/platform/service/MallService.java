package com.lys.platform.service;

import com.lys.platform.vo.FloorStoreInfoVo;
import com.lys.platform.vo.MallInfoVo;
import com.lys.platform.vo.MallQueryVo;
import com.lys.platform.vo.RequirementReqVo;
import com.lys.platform.vo.StoreQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 15:57
 * @version: 1.0
 */
public interface MallService {
    void likeMall(String phone, Integer mallId, Boolean like);

    void viewMall(String phone, Integer mallId);

    List<MallInfoVo> getLikeMallList(String phone);

    List<MallInfoVo> getViewMallList(String phone);

    MallInfoVo getMallDetail(Integer mallId);

    void clickMall(Integer mallId);

    Boolean requirementMall(RequirementReqVo requirementReqVo);

    Map<String, FloorStoreInfoVo> getStoreList(StoreQueryVo mallId);

    List<MallInfoVo> getMallList(MallQueryVo mallQueryVo);

}
