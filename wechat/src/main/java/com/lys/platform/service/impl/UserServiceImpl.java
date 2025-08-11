package com.lys.platform.service.impl;

import com.lys.platform.dao.UserMapper;
import com.lys.platform.entity.Customer;
import com.lys.platform.entity.WxUserInfo;
import com.lys.platform.enums.CertApproveStatus;
import com.lys.platform.service.ConsultantService;
import com.lys.platform.service.CustomerService;
import com.lys.platform.service.UserService;
import com.lys.platform.util.RedisUtil;
import com.lys.platform.vo.ConsultantVo;
import com.lys.platform.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description:
 * @Author jiangzhili
 * @Date 2025/7/26 15:58
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConsultantService consultantService;
    @Override
    public WxUserInfo getUserInfoByOpenid(String openid) {
        return userMapper.selectOne(new WxUserInfo().setOpenid(openid));
    }

    @Override
    public WxUserInfo creatUserInfo(WxUserInfo userInfo) {
        userMapper.insertUseGeneratedKeys(userInfo);
        return userInfo;
    }

    @Override
    public void updateUserInfo(UserInfoVo userInfoVo) {
        WxUserInfo userInfo = userMapper.selectByPrimaryKey(userInfoVo.getId());
        if (userInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        userInfo.setGender(userInfoVo.getGender());
        userInfo.setPhone(userInfoVo.getPhone());
        userInfo.setNickName(userInfoVo.getNickName());
        userInfo.setAvatarUrl(userInfoVo.getAvatarUrl());
        userInfo.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public UserInfoVo getUserInfoFromToken(HttpServletRequest request) {
        UserInfoVo userInfoVo = null;
        Integer userId = getUserIdFromToken(request);
        if (userId == null) {
            return null;
        }
        WxUserInfo wxUserInfo = userMapper.selectByPrimaryKey(userId);
        if (wxUserInfo != null) {
            String phone = wxUserInfo.getPhone();
            // 从customer表中获取认证信息
            Customer customer = customerService.getCustomerByPhone(phone);
            userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(wxUserInfo, userInfoVo);
            if (null != customer) {
                userInfoVo.setAuthType(customer.getAuthType());
                userInfoVo.setApproveStatus(customer.getApproveStatus());
                Integer consultantId = customer.getConsultantId();
                ConsultantVo consultantVo = null;
                if (null != consultantId) {
                    // 顾问信息丛后台人员表中查询
                    consultantVo = consultantService.getConsultantById(consultantId);
                }
                userInfoVo.setConsultant(consultantVo);
            } else {
                userInfoVo.setApproveStatus(CertApproveStatus.UNSUBMITTED.code());
            }
        }
        return userInfoVo;
    }

    @Override
    public Integer getUserIdFromToken(HttpServletRequest request) {
        // 获取header中的token信息
        String authToken = getAuthToken(request);
        if (authToken == null) return null;

        //获取redis中的token信息
        HashMap<String,Object> hashMap = (HashMap<String, Object>) redisUtil.hget(authToken);
        return Integer.parseInt(hashMap.get("id").toString());
    }

    @Override
    public String getPhoneFromToken(HttpServletRequest request) {
        String authToken = getAuthToken(request);
        if (authToken == null) return null;

        //获取redis中的token信息
        HashMap<String,Object> hashMap = (HashMap<String, Object>) redisUtil.hget(authToken);
        return hashMap.get("phone").toString();
    }

    @Override
    public Integer getCustomerIdFromToken(HttpServletRequest request) {
        // 获取header中的token信息
        String authToken = getAuthToken(request);
        if (authToken == null) return null;

        //获取redis中的token信息
        HashMap<String,Object> hashMap = (HashMap<String, Object>) redisUtil.hget(authToken);
        return Integer.parseInt(hashMap.get("customerId").toString());
    }

    private static String getAuthToken(HttpServletRequest request) {
        // 获取header中的token信息
        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring("Bearer ".length());
    }

}
