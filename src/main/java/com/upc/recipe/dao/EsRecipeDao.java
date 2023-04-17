package com.upc.recipe.dao;

import com.upc.recipe.nosql.elasticsearch.document.EsRecipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 搜索系统中的菜谱管理自定义Dao
 */
@Mapper
public interface EsRecipeDao {
    List<EsRecipe> getAllEsRecipeList(@Param("id") Integer id);
}
