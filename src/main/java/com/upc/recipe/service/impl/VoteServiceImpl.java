package com.upc.recipe.service.impl;

import com.upc.recipe.common.constants.RedisKeyConstants;
import com.upc.recipe.common.utils.RedisUtil;
import com.upc.recipe.component.VoteBox;
import com.upc.recipe.config.RedisConfig;
import com.upc.recipe.mapper.VoteMapperManual;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

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
    public boolean insertOneRecipeVote(VoteRecipe voteRecipe) {
        voteMapperManual.insertOneRecipeVotes(voteRecipe);
        return true;
    }

    @Override
    public boolean insertRecipeVoteList(List<VoteRecipe> voteRecipeList) {
        voteMapperManual.insertRecipeVotesBatch(voteRecipeList);
        return true;
    }
}
