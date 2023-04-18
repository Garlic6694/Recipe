package com.upc.recipe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class VoteControllerTest {

    @Test
    void vote() {
        Map<Integer, Byte> map = new HashMap<>();
        map.put(10, (byte) 0);
        map.put(20, (byte) 0);
        map.put(30, (byte) 1);
        map.put(40, (byte) 0);
        map.put(50, (byte) 1);
        System.out.println("\n3. Export Map Value to List..., say no to banana");
        List<Byte> result3 = map.values().stream()
                .filter(x -> x.equals((byte)0))
                .collect(Collectors.toList());
        result3.forEach(System.out::println);

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