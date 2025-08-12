package com.lys.platform.service;
import com.lys.platform.dao.UserMapper;
import com.lys.platform.entity.WxUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 用户认证、权限、使用security的表单登录时会被调用(自定义登录请忽略)
 * @author: jamesluozhiwei
 */
@Component
@Slf4j
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 若使用security表单鉴权则需实现该方法，通过username获取用户信息（密码、权限等等）
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        WxUserInfo userInfo = userMapper.selectByPrimaryKey(id);

        //通过username查询用户
        //根据自己的业务获取用户信息
        //SelfUserDetails user = userMapper.getUser(username);
        //模拟从数据库获取到用户信息
        if(userInfo == null){
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }

//        Set authoritiesSet = new HashSet();
        // 模拟从数据库中获取用户权限
//        authoritiesSet.add(new SimpleGrantedAuthority("test:list"));
    /*    authoritiesSet.add(new SimpleGrantedAuthority("test:add"));
        userInfo.setAuthorities(authoritiesSet);*/

        log.info("用户{}验证通过",userInfo);
        return userInfo;
    }
}
