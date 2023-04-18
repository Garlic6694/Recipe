package com.upc.recipe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoteControllerTest {

    @Test
    void vote() {
        VoteDocument voteDoc = new VoteDocument();
        voteDoc.setUserId(12);
        voteDoc.setRecipeId(10);
        voteDoc.setVoting((byte) 1);

        VoteRecipe voteRecipe = new VoteRecipe();
        voteRecipe.setRecipeId(10);
        voteRecipe.setVotes(200);


        JSONObject fullObject = new JSONObject();
        fullObject.put("voteDoc", voteDoc);
        fullObject.put("voteRecipe", voteRecipe);

        System.out.println(fullObject.toString());

        JSONObject resultJson = JSON.parseObject(fullObject.toString());
        VoteDocument voteDocument = resultJson.getObject("voteDoc", VoteDocument.class);
        System.out.println(voteDocument);

    }

    @Test
    void unvote() {
    }

    @Test
    void getUserVotedRecipes() {
    }

    @Test
    void getRecipeVotes() {
    }

    @Test
    void recipeVotes() {
    }
}