package com.upc.recipe.controller;

import com.alibaba.fastjson.JSONObject;
import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.common.factory.SingletonFactory;
import com.upc.recipe.common.utils.JwtTokenUtil;
import com.upc.recipe.component.rmq.RocketMQTemplateProducer;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.service.VoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = "VoteController", value = "投票功能")
@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Value("${rocketmq.topic}")
    private String mqTopic;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final VoteDocument voteDoc = SingletonFactory.getInstance(VoteDocument.class);
    private final VoteRecipe voteRecipe = SingletonFactory.getInstance(VoteRecipe.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private VoteService voteService;

    @Autowired
    private RocketMQTemplateProducer rocketMQTemplateProducer;

    @ApiOperation("点赞或取消点赞")
    @PostMapping(value = "/voting")
    public CommonResult<?> vote(@RequestParam int userId,
                                @RequestParam int recipeId,
                                @RequestParam boolean voting,
                                HttpServletRequest request) {
        String authHeader = request.getHeader(this.tokenHeader);
        String username = null;
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            username = jwtTokenUtil.getUserNameFromToken(authToken);
        }
        log.info("voting username:{}", username);

        byte voteOrNot = (byte) (voting ? 1 : 0);
        Integer sumVote = voteService.like(userId, recipeId, voting);

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


    @ApiOperation("根据用户id获取点赞列表")
    @GetMapping(value = "/getUserVotedRecipeIds")
    public CommonResult<?> getUserVotedRecipes(@RequestParam Integer userId) {
        List<Integer> votedRecipesIds = voteService.getUserVotedRecipes(userId);
        List<Integer> unvotedRecipesIds = voteService.getUserUnVotedRecipes(userId);
        List<Integer> result = votedRecipesIds.stream().filter(item -> !unvotedRecipesIds.contains(item)).collect(toList());
        return CommonResult.success(result);
    }

    @ApiOperation("根据菜谱id获取点赞总数")
    @GetMapping(value = "/getRecipeVotes")
    public CommonResult<?> getRecipeVotes(@RequestParam Integer recipeId) {
        Integer votes = voteService.getRecipeVotes(recipeId);
        return CommonResult.success(votes);
    }

}
