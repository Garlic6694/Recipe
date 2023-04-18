package com.upc.recipe.mbg.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户权限表
 * ums_permission
 * @author canwe
 * @date 2023-04-18 10:37:32
 */
@Data
public class UmsPermission implements Serializable {
    /**
     */
    private Integer id;

    /**
     * 名称
     *
     * @mbg.generated
     */
    /**
     * 名称
     */
    private String name;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    /**
     * 权限值
     */
    private String value;

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
        sb.append(", name=").append(name);
        sb.append(", value=").append(value);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}