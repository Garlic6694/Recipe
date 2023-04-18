package com.upc.recipe.controller;

import com.alibaba.fastjson.JSONObject;
import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.common.factory.SingletonFactory;
import com.upc.recipe.common.utils.RedisUtil;
import com.upc.recipe.component.rmq.RocketMQTemplateProducer;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Value("${rocketmq.topic}")
    private String mqTopic;

    private final VoteDocument voteDoc = SingletonFactory.getInstance(VoteDocument.class);
    private final VoteRecipe voteRecipe = SingletonFactory.getInstance(VoteRecipe.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private RocketMQTemplateProducer rocketMQTemplateProducer;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "/voting")
    public CommonResult<?> vote(@RequestParam int userId, @RequestParam int recipeId, @RequestParam boolean voting) {
        byte voteOrNot = (byte) (voting ? 1 : 0);
        Integer sumVote = voteService.vote(userId, recipeId, voting);

        voteDoc.setUserId(userId);
        voteDoc.setRecipeId(recipeId);
        voteDoc.setVoting(voteOrNot);

        voteRecipe.setRecipeId(recipeId);
        voteRecipe.setVotes(sumVote);

        JSONObject fullObject = new JSONObject();
        fullObject.put("voteDoc", voteDoc);
        fullObject.put("voteRecipe", voteRecipe);

        rocketMQTemplateProducer.asyncSendDelay(mqTopic, fullObject.toString(), 2000, 3);

        return CommonResult.success(fullObject);
    }


    @GetMapping(value = "/getUserVotedRecipeIds")
    public CommonResult<?> getUserVotedRecipes(@RequestParam Integer userId) {
        List<Integer> votedRecipesIds = voteService.getUserVotedRecipes(userId);
        List<Integer> unvotedRecipesIds = voteService.getUserUnVotedRecipes(userId);
        List<Integer> result = votedRecipesIds.stream().filter(item -> !unvotedRecipesIds.contains(item)).collect(toList());
        return CommonResult.success(result);
    }

    @GetMapping(value = "/getRecipeVotes")
    public CommonResult<?> getRecipeVotes(@RequestParam Integer recipeId) {
        Integer votes = voteService.getRecipeVotes(recipeId);
        return CommonResult.success(votes);
    }


    @PostMapping(value = "/insertRecipesVote")
    public CommonResult<?> recipeVotes(@RequestBody List<VoteRecipe> voteRecipeList) {
        boolean result = voteService.insertRecipeVoteList(voteRecipeList);
        return CommonResult.success(result);
    }
}
