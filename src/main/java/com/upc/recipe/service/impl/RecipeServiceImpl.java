package com.upc.recipe.service.impl;

import com.upc.recipe.common.utils.JieBaCut;
import com.upc.recipe.mapper.RecipeMapperManual;
import com.upc.recipe.mbg.mapper.RecipeMapper;
import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.mbg.model.Video;
import com.upc.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    @Qualifier(value = "recipeMapperManual")
    private RecipeMapperManual recipeMapperManual;

    @Autowired
    @Qualifier(value = "recipeMapper")
    private RecipeMapper recipeMapper;

    @Override
    public List<Recipe> queryByTitle(String title, Integer page, Integer limit) {
        List<String> jieBaList = JieBaCut.segmenter.sentenceProcess(title);
        List<Recipe> recipeList = new ArrayList<>();
        for (String voc : jieBaList) {
            recipeList.addAll(recipeMapperManual.queryByTitle(voc));
        }
        Set<Recipe> recipeSet = new HashSet<>(recipeList);//去重
        recipeList = new ArrayList<>(recipeSet);
        return recipeList;
    }

    @Override
    public List<Recipe> queryByCats(String cats, Integer page, Integer limit) {
        List<Recipe> recipeList = recipeMapperManual.queryByCats(cats);
        return recipeList;
    }

    @Override
    public List<Recipe> getRandomRecipe() {
        List<Recipe> recipeList = recipeMapperManual.getRandomRecipe();
        return recipeList;
    }

    @Override
    public List<Video> getVideos() {
        List<Video> list = recipeMapperManual.getVideos();
        return list;
    }

    @Override
    @Cacheable(value = {"queryById"}, keyGenerator = "keyGenerator")
    public List<Recipe> listAllRecipe() {
        List<Recipe> recipeList = recipeMapperManual.listAll();
        return recipeList;
    }

    @Override
    @Cacheable(value = {"queryById"}, keyGenerator = "keyGenerator")
    public List<Recipe> listPageRecipe(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        return recipeMapperManual.listPageRecipe(pageNum, pageSize);
    }

    @Override
    @Cacheable(value = {"queryById#10"}, keyGenerator = "keyGenerator")
    public Recipe queryById(Integer id) {
        Recipe recipe = recipeMapperManual.queryById(id);
        return recipe;
    }

    @Override
    public boolean updateScanCount(String id) {
        recipeMapperManual.updateScanCount(id);
        return true;
    }

    @Override
    public boolean updateLikeCount(String id) {
        recipeMapperManual.updateLikeCount(id);
        return true;
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        boolean flag = false;
        try {
            recipeMapperManual.addRecipe(recipe);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Recipe> getReviewRecipes(Integer page, Integer limit) {
        List<Recipe> recipeList = recipeMapperManual.getReviewRecipes();
        return recipeList;
    }

    @Override
    public boolean setReviewRecipes(String reviewRecipesId) {
        try {
            recipeMapperManual.setReviewRecipes(reviewRecipesId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRecipesById(String reviewRecipesId) {
        try {
            recipeMapperManual.deleteRecipesById(reviewRecipesId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
