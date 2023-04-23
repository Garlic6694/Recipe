package com.upc.recipe.common.collections;

import com.upc.recipe.common.constants.RedisKeyConstants;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UnprocessedMessage {

    private static final Map<String, VoteDocument> UNPROCESSED_MESSAGE_USER_VOTES_MAP = new ConcurrentHashMap<>();

    private static final Map<Integer, VoteRecipe> UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP = new ConcurrentHashMap<>();

    public void clear() {
        UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.clear();
        UNPROCESSED_MESSAGE_USER_VOTES_MAP.clear();
    }



    public boolean addUserVotes(VoteDocument voteDocument) {
        String key = RedisKeyConstants.getUserRecipeKey(voteDocument.getUserId(), voteDocument.getRecipeId());
        if (UNPROCESSED_MESSAGE_USER_VOTES_MAP.containsKey(key)) {
            UNPROCESSED_MESSAGE_USER_VOTES_MAP.get(key).setVoting(voteDocument.getVoting());
        } else {
            UNPROCESSED_MESSAGE_USER_VOTES_MAP.put(key, voteDocument);
        }

        return true;
    }

    public List<VoteDocument> getUserUnVoteList() {
        return UNPROCESSED_MESSAGE_USER_VOTES_MAP.values()
                .stream()
                .filter(x -> x.getVoting().equals((byte) 0))
                .collect(Collectors.toList());
    }

    public List<VoteDocument> getUserVoteList() {
        return UNPROCESSED_MESSAGE_USER_VOTES_MAP.values()
                .stream()
                .filter(x -> x.getVoting().equals((byte) 1))
                .collect(Collectors.toList());
    }

    public boolean addRecipeVotes(VoteRecipe voteRecipe) {
        Integer recipeId = voteRecipe.getRecipeId();
        if (UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.containsKey(recipeId)) {
            UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.get(recipeId).setVotes(voteRecipe.getVotes());
        } else {
            UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.put(voteRecipe.getRecipeId(), voteRecipe);
        }
        return true;
    }

    public List<VoteRecipe> getRecipeVoteList() {
        return new ArrayList<>(UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.values());
    }
}
