package com.upc.recipe.mapper;

import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VoteMapperManual {
    /**
     * 根据userId获取用户点赞过的帖子
     *
     * @param userId
     * @return
     */
    List<Integer> getVotedRecipeByUserId(Integer userId);

    List<Integer> getUserVotedRecipes(Integer userId);

    List<Integer> getUserUnVotedRecipes(Integer userId);


    /**
     * 根据recipeId得到菜谱被点赞数
     *
     * @param recipeId
     * @return
     */
    Integer getRecipeVotesByRecipeId(Integer recipeId);


    /**
     * 批量插入 用户 菜谱 点赞
     */
    void insertUserRecipeBatch(List<VoteDocument> voteDocumentList);

    void insertOneUserRecipe(VoteDocument voteDocument);

    /**
     * 批量插入 菜谱 总点赞
     */
    void insertRecipeVotesBatch(List<VoteRecipe> voteRecipeList);

    void insertOneRecipeVotes(VoteRecipe voteRecipe);


}
