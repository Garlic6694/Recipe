package com.upc.recipe.component;

import com.upc.recipe.common.constants.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 点赞箱
 */
@Repository
public class VoteBox {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DefaultRedisScript<Integer> voteScript;
    @Autowired
    private DefaultRedisScript<Integer> noVoteScript;

    /**
     * 给评价投票(点赞)，用户增加评价点赞记录，评价点赞次数+1.该操作是原子性、幂等性的。
     *
     * @param userId 投票人
     * @param userId 投票目标内容id
     * @return 返回当前最新点赞数
     */
    public Integer vote(int userId, int recipeId) {
        //使用lua脚本
        List<String> list = new ArrayList<>();
        list.add(RedisKeyConstants.getUserRecipeKey(userId, recipeId));
        list.add(RedisKeyConstants.getRecipeKey(recipeId));
        return redisTemplate.execute(voteScript, list);
    }

    /**
     * 取消给评价投票(点赞)，用户删除评价点赞记录，评价点赞次数-1.该操作是原子性、幂等性的。
     *
     * @param userId   投票人
     * @param recipeId 投票目标内容id
     * @return 返回当前最新点赞数
     */
    public Integer noVote(int userId, int recipeId) {
        //使用lua脚本
        List<String> list = new ArrayList<>();
        list.add(RedisKeyConstants.getUserRecipeKey(userId, recipeId));
        list.add(RedisKeyConstants.getRecipeKey(recipeId));
        return redisTemplate.execute(noVoteScript, list);
    }
}
