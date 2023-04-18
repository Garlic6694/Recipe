package com.upc.recipe.mbg.mapper;

import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.mbg.model.VoteRecipeExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteRecipeMapper {
    long countByExample(VoteRecipeExample example);

    int deleteByExample(VoteRecipeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteRecipe record);

    int insertSelective(VoteRecipe record);

    List<VoteRecipe> selectByExample(VoteRecipeExample example);

    VoteRecipe selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteRecipe record, @Param("example") VoteRecipeExample example);

    int updateByExample(@Param("record") VoteRecipe record, @Param("example") VoteRecipeExample example);

    int updateByPrimaryKeySelective(VoteRecipe record);

    int updateByPrimaryKey(VoteRecipe record);
}