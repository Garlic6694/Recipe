package com.upc.recipe.controller;

import com.upc.recipe.annotation.AnonymousAccess;
import com.upc.recipe.common.api.CommonPage;
import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.nosql.elasticsearch.document.EsRecipe;
import com.upc.recipe.service.EsRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esRecipe")
public class EsRecipeController {
    @Autowired
    private EsRecipeService esRecipeService;

    @PreAuthorize("hasAuthority('admin:update')")
    @PostMapping(value = "/importAll")
    public CommonResult<Integer> importALl() {
        int count = esRecipeService.importAll();
        return CommonResult.success(count);
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @GetMapping(value = "/delete/{id}")
    public CommonResult<?> delete(@PathVariable Integer id) {
        boolean flag = esRecipeService.delete(id);
        if (flag) {
            return CommonResult.success("delete es recipe success");

        } else {
            return CommonResult.failed("delete es recipe failed");
        }
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PostMapping(value = "/create/{id}")
    public CommonResult<?> create(@PathVariable Integer id) {
        EsRecipe esRecipe = esRecipeService.create(id);
        if (esRecipe != null) {
            return CommonResult.success(esRecipe);
        } else {
            return CommonResult.failed("create es recipe failed");
        }
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @PostMapping(value = "/delete/batch")
    public CommonResult<Object> delete(@RequestParam("ids") List<Integer> ids) {
        boolean flag = esRecipeService.delete(ids);
        if (flag) {
            return CommonResult.success("delete batch es recipe success");

        } else {
            return CommonResult.failed("delete batch es recipe failed");
        }
    }

    @AnonymousAccess
    @GetMapping(value = "/search/simple")
    public CommonResult<CommonPage<EsRecipe>> search(@RequestParam() String keyword,
                                                     @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                     @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsRecipe> esRecipePage = esRecipeService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esRecipePage));
    }

}
