package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.service.MallService;
import com.lys.platform.vo.FloorStoreInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:店铺相关类
 * @Author jiangzhili
 * @Date 2025/7/24 11:22
 * @version: 1.0
 */
@RequestMapping("/store")
@RestController
public class StoreController {

    @Autowired
    private MallService mallService;

    @GetMapping("/list")
    public GenericResponse getStoreList(@RequestParam (name = "mallId")Integer mallId) {
        Map<String, FloorStoreInfoVo> storeList = mallService.getStoreList(mallId);
        return GenericResponse.response(ServiceError.NORMAL, storeList);
    }
}
