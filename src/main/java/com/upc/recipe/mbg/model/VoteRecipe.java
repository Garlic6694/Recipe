package com.upc.recipe.mbg.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * vote_recipe
 * @author canwe
 * @date 2023-04-18 10:37:32
 */
@Data
public class VoteRecipe implements Serializable {
    /**
     * ID
     *
     * @mbg.generated
     */
    /**
     * ID
     */
    private Integer id;

    /**
     * 菜谱id
     *
     * @mbg.generated
     */
    /**
     * 菜谱id
     */
    private Integer recipeId;

    /**
     * 投下/放弃这一点赞后，内容在此刻的点赞总数
     *
     * @mbg.generated
     */
    /**
     * 投下/放弃这一点赞后，内容在此刻的点赞总数
     */
    private Integer votes;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", recipeId=").append(recipeId);
        sb.append(", votes=").append(votes);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}