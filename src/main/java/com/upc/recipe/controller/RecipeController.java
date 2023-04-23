package com.upc.recipe.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.upc.recipe.annotation.AnonymousAccess;
import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RecipeController", value = "菜谱CRUD")
@RestController
@RequestMapping(value = "/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @ApiOperation("根据id获取菜谱")
    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping(value = "/getRecipe")
    public CommonResult<Recipe> getRecipe(@RequestParam(value = "id") Integer id) {
        Recipe recipe = recipeService.queryById(id);
        return CommonResult.success(recipe);
    }

    @ApiOperation("分页获取菜谱(PageHelper)")
    @AnonymousAccess
    @GetMapping(value = "/listAllRecipe")
    public CommonResult<PageInfo<Recipe>> listAllRecipe(@RequestParam(value = "pageNum") Integer pageNum,
                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Recipe> recipeList = recipeService.listAllRecipe();
        PageInfo<Recipe> page = new PageInfo<>(recipeList);
        return CommonResult.success(page);
    }

    @ApiOperation("分页获取菜谱(SQL)")
    @AnonymousAccess
    @GetMapping(value = "/listPageRecipe")
    public CommonResult<List<Recipe>> listPageRecipe(@RequestParam(value = "pageNum") Integer pageNum,
                                                     @RequestParam(value = "pageSize") Integer pageSize) {
        List<Recipe> recipeList = recipeService.listPageRecipe(pageNum, pageSize);
        return CommonResult.success(recipeList);
    }
}
