package com.upc.recipe.mbg.mapper;

import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.mbg.model.RecipeExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecipeMapper {
    long countByExample(RecipeExample example);

    int deleteByExample(RecipeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Recipe record);

    int insertSelective(Recipe record);

    List<Recipe> selectByExampleWithBLOBs(RecipeExample example);

    List<Recipe> selectByExample(RecipeExample example);

    Recipe selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Recipe record, @Param("example") RecipeExample example);

    int updateByExampleWithBLOBs(@Param("record") Recipe record, @Param("example") RecipeExample example);

    int updateByExample(@Param("record") Recipe record, @Param("example") RecipeExample example);

    int updateByPrimaryKeySelective(Recipe record);

    int updateByPrimaryKeyWithBLOBs(Recipe record);

    int updateByPrimaryKey(Recipe record);
}