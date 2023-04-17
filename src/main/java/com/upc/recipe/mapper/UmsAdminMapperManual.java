package com.upc.recipe.mapper;

import com.upc.recipe.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UmsAdminMapperManual {
    /**
     * 获取用户所有权限
     */
    List<UmsPermission> getPermissionList(@Param("userId") Integer userId);
}
