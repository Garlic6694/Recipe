package com.upc.recipe.service.impl;

import com.upc.recipe.common.constants.RedisKeyConstants;
import com.upc.recipe.common.utils.RedisUtil;
import com.upc.recipe.component.VoteBox;
import com.upc.recipe.mapper.VoteMapperManual;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Value("${spring.redis.expire-time-like}")
    private long expireTimeOfLike;

    @Autowired
    private VoteBox voteBox;

    @Autowired
    private VoteMapperManual voteMapperManual;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int vote(int userId, int recipeId, boolean voting) {
        return voting ? voteBox.vote(userId, recipeId) : voteBox.noVote(userId, recipeId);
    }

    @Override
    public int like(int userId, int recipeId, boolean voting) {
        String userVoteKey = RedisKeyConstants.getUserRecipeKey(userId, recipeId);
        boolean hasUserVoteKey = redisUtil.hasKey(userVoteKey);//redis有无点赞信息

        String recipeKey = RedisKeyConstants.getRecipeKey(recipeId);
        boolean hasRecipeKey = redisUtil.hasKey(recipeKey);//redis有无菜谱总点赞数信息

        boolean votedOrNot = voteMapperManual.isUserVotedOrNot(userId, recipeId) != null; //是否存在用户id-菜谱id 点赞信息
        if (votedOrNot && !hasUserVoteKey) { //db 有信息 redis无
            //读取db写入redis 用户菜谱信息
            redisUtil.set(userVoteKey, 1, expireTimeOfLike);
        }

        Integer Votes = voteMapperManual.getRecipeVotesByRecipeId(recipeId);//db有菜谱总点赞信息
        if (Votes != null && !hasRecipeKey) {
            //读取db写入redis 用户菜谱信息
            redisUtil.set(recipeKey, Votes, -1);
        }

        return voting ? voteBox.vote(userId, recipeId) : voteBox.noVote(userId, recipeId);
    }

    @Override
    public List<Integer> getUserVotedRecipes(Integer userId) {
        return voteMapperManual.getUserVotedRecipes(userId);
    }

    @Override
    public List<Integer> getUserUnVotedRecipes(Integer userId) {
        return voteMapperManual.getUserUnVotedRecipes(userId);
    }

    @Override
    public Integer getRecipeVotes(Integer recipeId) {
        Integer votes = (Integer) redisUtil.get(RedisKeyConstants.getRecipeKey(recipeId));
        if (votes == null) {
            votes = voteMapperManual.getRecipeVotesByRecipeId(recipeId);
            redisUtil.set(RedisKeyConstants.getRecipeKey(recipeId), votes);
        }
        return votes;
    }

    @Override
    public boolean insertOneUserVote(VoteDocument voteDocument) {
        voteMapperManual.insertOneUserRecipe(voteDocument);
        return true;
    }

    @Override
    public boolean insertUserVoteList(List<VoteDocument> voteDocumentList) {
        voteMapperManual.insertUserRecipeBatch(voteDocumentList);
        return true;
    }

    @Override
    public boolean deleteUserUnVoteList(List<VoteDocument> voteDocumentList) {
        voteMapperManual.deleteUserRecipeBatch(voteDocumentList);
        return true;
    }

    @Override
    public boolean insertOneRecipeVote(VoteRecipe voteRecipe) {
        voteMapperManual.insertOneRecipeVotes(voteRecipe);
        return true;
    }

    @Override
    public boolean insertRecipeVoteList(List<VoteRecipe> voteRecipeList) {
        voteMapperManual.insertRecipeVotesBatch(voteRecipeList);
        return true;
    }

    @Override
    public boolean deleteRecipeVoteList(List<VoteRecipe> voteRecipeList) {
        voteMapperManual.deleteRecipeVotesBatch(voteRecipeList);
        return true;
    }
}
