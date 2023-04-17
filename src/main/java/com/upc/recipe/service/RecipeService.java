package com.upc.recipe.service;


import com.upc.recipe.common.api.result.ResultData;
import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.mbg.model.Video;

import java.util.List;

public interface RecipeService {
    List<Recipe> queryByTitle(String title, Integer page, Integer limit);

    List<Recipe> queryByCats(String cats, Integer page, Integer limit);

    List<Recipe> getRandomRecipe();

    List<Video> getVideos();

    List<Recipe> listAllRecipe();

    List<Recipe> listPageRecipe(Integer pageNum, Integer pageSize);

    Recipe queryById(Integer id);

    boolean updateScanCount(String id);

    boolean updateLikeCount(String id);

    boolean addRecipe(Recipe recipe);

    List<Recipe> getReviewRecipes(Integer page, Integer limit);

    boolean setReviewRecipes(String reviewRecipesId);

    boolean deleteRecipesById(String reviewRecipesId);

}
