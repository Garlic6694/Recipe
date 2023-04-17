package com.upc.recipe.mbg.model;

import java.io.Serializable;
import lombok.Data;

/**
 * recipe
 * @author canwe
 * @date 2023-04-12 19:42:51
 */
@Data
public class Recipe implements Serializable {
    /**
     */
    private Integer id;

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
     */
    private String title;

    /**
     */
    private String des;

    /**
     */
    private String cats;

    /**
     */
    private String ing;

    /**
     */
    private String steps;

    /**
     */
    private String tip;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", status=").append(status);
        sb.append(", title=").append(title);
        sb.append(", des=").append(des);
        sb.append(", cats=").append(cats);
        sb.append(", ing=").append(ing);
        sb.append(", steps=").append(steps);
        sb.append(", tip=").append(tip);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}