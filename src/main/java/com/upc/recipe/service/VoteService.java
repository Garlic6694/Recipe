package com.upc.recipe.service;

import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;

import java.util.List;

public interface VoteService {

    int vote(int userId, int recipeId, boolean voting);

    /**
     * 根据用户id和菜谱id已经是否赞
     * <p>
     * 缓存中有UserId:1::RecipeId:10 则直接调用vote
     * </p>
     * <p>
     * 缓存中无，则先读取数据库，库中有数据，建立缓存{
     * RecipeId - Votes key:[RecipeId:10] value:[20]
     * UserId:1::RecipeId:10 - Voting? key:[UserId:1::RecipeId:10] value:[1] (空是取消点赞状态，1是已经点赞了)
     * }
     * </p>
     */
    int like(int userId, int recipeId, boolean voting);

    List<Integer> getUserVotedRecipes(Integer userId);

    List<Integer> getUserUnVotedRecipes(Integer userId);

    Integer getRecipeVotes(Integer recipeId);

    boolean insertOneUserVote(VoteDocument voteDocument);

    boolean insertUserVoteList(List<VoteDocument> voteDocumentList);

    boolean deleteUserUnVoteList(List<VoteDocument> voteDocumentList);


    boolean insertOneRecipeVote(VoteRecipe voteRecipe);

    boolean insertRecipeVoteList(List<VoteRecipe> voteRecipeList);

    boolean deleteRecipeVoteList(List<VoteRecipe> voteRecipeList);


}
