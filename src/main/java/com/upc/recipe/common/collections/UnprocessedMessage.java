package com.upc.recipe.common.collections;

import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UnprocessedMessage {

    private static final List<VoteDocument> UNPROCESSED_MESSAGE_VD_HIS_LIST = new ArrayList<>();

    private static final Map<Integer, VoteRecipe> UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP = new ConcurrentHashMap<>();

    public void clear() {
        UNPROCESSED_MESSAGE_VD_HIS_LIST.clear();
        UNPROCESSED_MESSAGE_RECIPE_VOTES_MAP.clear();
    }

    public boolean addHistory(VoteDocument voteDocument) {
        return UNPROCESSED_MESSAGE_VD_HIS_LIST.add(voteDocument);
    }

    public List<VoteDocument> getHistoryList() {
        return UNPROCESSED_MESSAGE_VD_HIS_LIST;
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
