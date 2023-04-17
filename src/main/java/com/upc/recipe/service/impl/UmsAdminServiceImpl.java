package com.upc.recipe.service.impl;

import com.upc.recipe.common.utils.JwtTokenUtil;
import com.upc.recipe.mapper.UmsAdminMapperManual;
import com.upc.recipe.mbg.mapper.UmsUserMapper;
import com.upc.recipe.mbg.model.UmsPermission;
import com.upc.recipe.mbg.model.UmsUser;
import com.upc.recipe.mbg.model.UmsUserExample;
import com.upc.recipe.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsUserMapper userMapper;
    @Autowired
    private UmsAdminMapperManual adminMapperManual;

    /**
     * 根据用户名获取后台管理员
     *
     * @param username
     */
    @Override
    public UmsUser getAdminByUsername(String username) {
        UmsUserExample example = new UmsUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsUser> adminList = userMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    /**
     * 注册功能
     *
     * @param umsAdminParam
     */
    @Override
    public UmsUser register(UmsUser umsAdminParam) {
        UmsUser umsAdmin = new UmsUser();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsUserExample example = new UmsUserExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsUser> umsAdminList = userMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        userMapper.insert(umsAdmin);
        return umsAdmin;
    }

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 获取用户所有权限
     *
     * @param adminId user id
     */
    @Override
    public List<UmsPermission> getPermissionList(Integer adminId) {
        return adminMapperManual.getPermissionList(adminId);
    }
}
