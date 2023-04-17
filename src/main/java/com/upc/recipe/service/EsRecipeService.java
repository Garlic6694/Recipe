package com.upc.recipe.service;

import com.upc.recipe.nosql.elasticsearch.document.EsRecipe;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EsRecipeService {
    /**
     * 从数据库中导入所有菜谱到ES
     */
    int importAll();

    /**
     * 根据id删除菜谱
     *
     * @return
     */
    boolean delete(Integer id);

    /**
     * 根据id创建菜谱
     */
    EsRecipe create(Integer id);

    /**
     * 批量删除菜谱
     *
     * @return
     */
    boolean delete(List<Integer> ids);

    /**
     * 根据关键字搜索名称或者描述或者步骤
     */
    Page<EsRecipe> search(String keyword, Integer pageNum, Integer pageSize);
}
