package com.lys.platform.controller;

import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.entity.Customer;
import com.lys.platform.enums.CertApproveStatus;
import com.lys.platform.service.CustomerService;
import com.lys.platform.service.MallService;
import com.lys.platform.service.UserService;
import com.lys.platform.vo.MallInfoVo;
import com.lys.platform.vo.MallQueryVo;
import com.lys.platform.vo.RequirementReqVo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/24 11:25
 * @version: 1.0
 */
@RestController
@RequestMapping("/mall")
public class MallController {
    @Autowired
    private MallService mallService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/list")
    public GenericResponse getMallList(@RequestBody @Validated MallQueryVo  mallQueryVo) {
        List<MallInfoVo> matchedMallInfoList = mallService.getMallList(mallQueryVo);
        return GenericResponse.response(ServiceError.NORMAL, matchedMallInfoList);
    }

    @GetMapping("/getDetail")
    public GenericResponse getMallDetail(HttpServletRequest request,
            @ApiParam(name = "mallId", value = "商场id", required = true)
            @RequestParam(name = "mallId") Integer mallId) {
        // 增加一次访问次数,先直接写到库,用户量上来再考虑从redis定时更新
        mallService.clickMall(mallId);
        // 获取商场详情
        MallInfoVo mallInfoVo = mallService.getMallDetail(mallId);
        if (mallInfoVo == null) {
            return GenericResponse.response(ServiceError.NORMAL, null);
        }
        // 获取商场详情前需要增加访问记录,没有登录也需要记录,每点击一次增加一条浏览记录
        String phone = userService.getPhoneFromToken(request);
        if (null != phone) {
            mallService.viewMall(phone, mallId);
        } else {
            // 没有登录的时候手机号码脱敏处理
            String originPhone = mallInfoVo.getPhone();
            String maskPhone = maskPhoneNumber(originPhone);
            mallInfoVo.setPhone(maskPhone);
        }
        return GenericResponse.response(ServiceError.NORMAL, mallInfoVo);
    }

    public static String maskPhoneNumber(String phoneNumber) {
        // 校验电话号码长度是否为11
        if (!StringUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11) {
            // 取前三位，替换中间五位，取后两位
            return phoneNumber.substring(0, 3) + "*****" + phoneNumber.substring(8);
        }
        // 不正确的电话号码,直接返回固定值
        return "1*****88888";


    }

    // 收藏和取消收藏
    @GetMapping("/like")
    public GenericResponse likeMall(HttpServletRequest request,
                                    @ApiParam(name = "mallId", value = "商场id", required = true)
            @RequestParam(name = "mallId") Integer mallId,
                                    @ApiParam(name = "like", value = "商场id", required = true)
            @RequestParam(name = "like") Boolean like) {
        String phone = userService.getPhoneFromToken(request);
        mallService.likeMall(phone, mallId, like);
        return GenericResponse.response(ServiceError.NORMAL);
    }

    // 收藏列表
    @GetMapping("/like/list")
    public GenericResponse getLikeMallList(HttpServletRequest request) {
        String phone = userService.getPhoneFromToken(request);
        List<MallInfoVo> mallLikeList = mallService.getLikeMallList(phone);
        return GenericResponse.response(ServiceError.NORMAL, mallLikeList);
    }

    // 访问列表
    @GetMapping("/view/list")
    public GenericResponse getViewMallList(HttpServletRequest request) {
        String phone = userService.getPhoneFromToken(request);
        List<MallInfoVo> mallViewList = mallService.getViewMallList(phone);
        return GenericResponse.response(ServiceError.NORMAL, mallViewList);
    }

    @PostMapping("/requirement")
    public GenericResponse requirementMall(HttpServletRequest request, @RequestBody @Validated RequirementReqVo requirementReqVo) {
        Integer customerId = userService.getCustomerIdFromToken(request);
        // 获取认证信息.没有认证信息,或认证信息被拒绝,返回错误,提示去认证
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null || customer.getApproveStatus().equals(CertApproveStatus.REJECTED.code())) {
            return GenericResponse.response(ServiceError.GLOBAL_ERR_NO_CERT);
        }
        if (customer.getApproveStatus().equals(CertApproveStatus.UNREVIEWED.code())) {
            // 认证信息审核中,提示请稍后
            return GenericResponse.response(ServiceError.GLOBAL_ERR_CERTING);
        }
        requirementReqVo.setCustomerId(customerId);
        Boolean success = mallService.requirementMall(requirementReqVo);
        // 存在相同的提交信息,提示无需重复提交
        if (!success) {
            return GenericResponse.response(ServiceError.GLOBAL_ERR_REQUIREMENT_REPEATED);
        }
        // 返回false,表示存在重复数据,提交失败
        return GenericResponse.response(ServiceError.NORMAL);
    }

}
