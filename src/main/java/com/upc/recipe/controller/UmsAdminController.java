package com.upc.recipe.controller;

import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.dto.UmsAdminLoginParam;
import com.upc.recipe.mbg.model.UmsPermission;
import com.upc.recipe.mbg.model.UmsUser;
import com.upc.recipe.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping(value = "/register")
    @ResponseBody
    public CommonResult<UmsUser> register(@RequestBody UmsUser umsAdminParam, BindingResult result) {
        UmsUser umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public CommonResult<?> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String username = umsAdminLoginParam.getUsername();
        String password = umsAdminLoginParam.getPassword();
        log.info("username : [{}]", username);
        log.info("password : [{}]", password);

        String token = adminService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping(value = "/permission/{adminId}")
    @ResponseBody
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Integer adminId) {
        List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
