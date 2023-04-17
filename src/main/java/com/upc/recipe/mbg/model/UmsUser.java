package com.upc.recipe.mbg.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 后台用户表
 * ums_user
 * @author canwe
 * @date 2023-04-12 19:42:51
 */
@Data
public class UmsUser implements Serializable {
    /**
     */
    private Integer id;

    /**
     */
    private String username;

    /**
     */
    private String password;

    /**
     * 角色id
     *
     * @mbg.generated
     */
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 启用状态；0->禁用；1->启用
     *
     * @mbg.generated
     */
    /**
     * 启用状态；0->禁用；1->启用
     */
    private Integer status;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", roleId=").append(roleId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}