package com.upc.recipe.nosql.elasticsearch.repository;

import com.upc.recipe.nosql.elasticsearch.document.EsRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

public interface EsRecipeRepository extends ElasticsearchRepository<EsRecipe, Integer> {
    /**
     * 搜索查询
     *
     * @param title 菜谱标题
     * @param des   描述
     * @param steps 制作步骤
     * @param page  分页信息
     * @return 分页
     */
    Page<EsRecipe> findByTitleOrDesOrSteps(String title, String des, String steps, Pageable page);
}
