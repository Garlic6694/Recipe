package com.upc.recipe.mbg.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * vote_document
 * @author canwe
 * @date 2023-04-18 10:37:32
 */
@Data
public class VoteDocument implements Serializable {
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
     * 用户id
     *
     * @mbg.generated
     */
    /**
     * 用户id
     */
    private Integer userId;

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
     * 点赞状态（0：取消点赞 1:点赞）
     *
     * @mbg.generated
     */
    /**
     * 点赞状态（0：取消点赞 1:点赞）
     */
    private Byte voting;

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
        sb.append(", userId=").append(userId);
        sb.append(", recipeId=").append(recipeId);
        sb.append(", voting=").append(voting);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}