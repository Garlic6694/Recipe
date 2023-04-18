package com.upc.recipe.common.constants;

import java.text.MessageFormat;

public class RedisKeyConstants {
    private static String VOTE_USER_PATTERN = "UserId:{0}::RecipeId:{1}";
    private static String VOTE_SUM_PATTERN = "RecipeId:{0}";

    public static String getUserRecipeKey(Integer userId, Integer RecipeId) {
        return MessageFormat.format(VOTE_USER_PATTERN, userId, RecipeId);
    }

    public static String getRecipeKey(Integer RecipeId) {
        return MessageFormat.format(VOTE_SUM_PATTERN, RecipeId);
    }

}
