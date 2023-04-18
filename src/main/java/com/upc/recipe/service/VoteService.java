package com.upc.recipe.service;

import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;

import java.util.List;

public interface VoteService {

    int vote(int userId, int recipeId, boolean voting);

    List<Integer> getUserVotedRecipes(Integer userId);

    List<Integer> getUserUnVotedRecipes(Integer userId);

    Integer getRecipeVotes(Integer recipeId);

    boolean insertOneUserVote(VoteDocument voteDocument);

    boolean insertUserVoteList(List<VoteDocument> voteDocumentList);

    boolean insertOneRecipeVote(VoteRecipe voteRecipe);

    boolean insertRecipeVoteList(List<VoteRecipe> voteRecipeList);

}
