package com.lys.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lys.platform.common.GenericResponse;
import com.lys.platform.common.ServiceError;
import com.lys.platform.entity.Customer;
import com.lys.platform.entity.WxUserInfo;
import com.lys.platform.enums.CertApproveStatus;
import com.lys.platform.enums.CustomerType;
import com.lys.platform.service.CustomerService;
import com.lys.platform.service.UserService;
import com.lys.platform.service.WeChatService;
import com.lys.platform.util.Jcode2SessionUtil;
import com.lys.platform.util.JwtTokenUtil;
import com.lys.platform.util.RedisUtil;
import com.lys.platform.vo.LoginVo;
import com.lys.platform.vo.UserInfoVo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 微信业务实现类
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Value("${weChat.appid}")
    private String appid;

    @Value("${weChat.secret}")
    private String secret;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public GenericResponse wxLogin(LoginVo loginVO) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        String code = loginVO.getCode();
        JSONObject sessionInfo = JSONObject.parseObject(jcode2Session(code));

        Assert.notNull(sessionInfo,"code 无效");

        // 获取用户唯一标识符 openid成功
        String openid = sessionInfo.getString("openid");
        if (null == openid) {
            throw new RuntimeException("拉取小程序信息报错");
        }
        String sessionKey = sessionInfo.getString("session_key");
        // 解析用户加密信息
        String decryptedData = decrypt(sessionKey, loginVO.getEncryptedData(), loginVO.getIv());

        // 从解密信息获得phone
        String phone = null;
        Map<String, Object> dataMap = objectMapper.readValue(decryptedData,
                new TypeReference<Map<String, Object>>() {});

        Object purePhoneNumber = dataMap.get("purePhoneNumber");
        if (purePhoneNumber != null) {
            phone = purePhoneNumber.toString();
        }
        String nickName = String.valueOf(dataMap.getOrDefault("nickName",  ""));
        String avatarUrl = String.valueOf(dataMap.getOrDefault("avatarUrl",  ""));
        Integer gender = (Integer) dataMap.getOrDefault("gender",  1);

      /*  String openid = " o6INMvvtSgn1E9w6hW2-7TGvtjeE";
        String phone = "19130856031";
        String nickName = "Chocolate";
        String avatarUrl = "https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132";
        Integer gender = 1;*/
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("获取手机号失败");
        }
        WxUserInfo userInfo = userService.getUserInfoByOpenid(openid);
        Date now = new Date();
        if (null == userInfo) {
//            UserInfoVo userInfoVO = loginVO.getUserInfo();
            userInfo = new WxUserInfo();
            userInfo.setOpenid(openid);
            userInfo.setPhone(phone);
            userInfo.setNickName(nickName);
            userInfo.setAvatarUrl(avatarUrl);
            userInfo.setGender(gender);
            userInfo.setCreateTime(now);
            userService.creatUserInfo(userInfo);
        }
        // 通过phone去查询customers是否有预制的信息
        Customer customer = customerService.getCustomerByPhone(phone);
        if (null == customer) {
            // 如果没有需要添加一条customer数据
            customer = new Customer()
                    .setPhone(phone)
                    .setName(nickName)
                    .setCustomerType(CustomerType.BRAND.code())
                    .setApproveStatus(CertApproveStatus.UNSUBMITTED.code())
                    .setRegisTime(now)
                    .setCreateTime(now);
//                  .setName().setAuthType().setConsultantId()
            customerService.createCustomer(customer);

        }

        // 模拟从数据库获取用户信息
        /*Set authoritiesSet = new HashSet();
        // 模拟从数据库中获取用户权限
        authoritiesSet.add(new SimpleGrantedAuthority("test:add"));
        authoritiesSet.add(new SimpleGrantedAuthority("test:list"));
        authoritiesSet.add(new SimpleGrantedAuthority("ddd:list"));
        user.setAuthorities(authoritiesSet);*/
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",userInfo.getId().toString());
        hashMap.put("phone",userInfo.getPhone());
        hashMap.put("customenId",customer.getId().toString());
//        hashMap.put("authorities",authoritiesSet);
        String token = JwtTokenUtil.generateToken(userInfo);
        redisUtil.hset(token,hashMap);

        return GenericResponse.response(ServiceError.NORMAL,token);
    }

    @Override
    public GenericResponse wxLogout(HttpServletRequest request) {
        // 获取header中的token信息
        String authHeader = request.getHeader("Authorization");
//        response.setCharacterEncoding("utf-8");
        if (null == authHeader || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);//token格式不正确
            return null;
        }
        String authToken = authHeader.substring("Bearer ".length());
        //获取redis中的token信息
        redisUtil.delete(authToken);
        return null;
    }

    /**
     * 登录凭证校验
     * @param code
     * @return
     * @throws Exception
     */
    private String jcode2Session(String code)throws Exception{
        String sessionInfo = Jcode2SessionUtil.jscode2session(appid,secret,code,"authorization_code");//登录grantType固定
        log.info(sessionInfo);
        return sessionInfo;
    }

    public String decrypt(String sessionKey, String encryptedData, String iv) {
        try {
            byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] ivBytes = Base64.getDecoder().decode(iv);

            SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(encryptedDataBytes);

            return new String(decrypted, "UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException("解密失败", ex);
        }
    }

}
