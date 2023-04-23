package com.upc.recipe.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * vote_document
 * @author canwe
 * @date 2023-04-22 00:17:28
 */
@Data
public class VoteDocument implements Serializable {
    @ApiModelProperty(value = "ID")
    /**
     * ID
     */
    private Integer id;

    @ApiModelProperty(value = "用户id")
    /**
     * 用户id
     */
    private Integer userId;

    @ApiModelProperty(value = "菜谱id")
    /**
     * 菜谱id
     */
    private Integer recipeId;

    @ApiModelProperty(value = "点赞状态（0：取消点赞 1:点赞）")
    /**
     * 点赞状态（0：取消点赞 1:点赞）
     */
    private Byte voting;

    @ApiModelProperty(value = "创建时间")
    /**
     * 创建时间
     */
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
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