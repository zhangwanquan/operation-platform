package com.lys.platform.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/29 13:50
 * @version: 1.0
 */
@Data
public class FloorStoreInfoVo {
    List<StoreInfoVo> stores;
    long totalCount;

    public FloorStoreInfoVo(List<StoreInfoVo> voList, int size) {
        this.stores = voList;
        this.totalCount = size;
    }
}
