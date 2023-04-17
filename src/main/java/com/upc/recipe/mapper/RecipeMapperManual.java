package com.upc.recipe.mapper;

import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.mbg.model.Video;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecipeMapperManual {
    Recipe queryById(int id);

    @Select("SELECT * FROM recipe")
    List<Recipe> listAll();

    /**
     * @param offsetNum 跳过前面pageNum-1页
     * @param limitNum  当前页面
     * @return size = limitNum recipe's list
     */
    List<Recipe> listPageRecipe(int offsetNum, int limitNum);

    @Select("SELECT * FROM recipe WHERE title like CONCAT('%',#{title},'%')")
    List<Recipe> queryByTitle(String title);

    @Select("SELECT * FROM recipe WHERE cats like CONCAT('%',#{cats},'%')")
    List<Recipe> queryByCats(String cats);

    @Select("SELECT * FROM recipe AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(id) FROM recipe)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id ASC LIMIT 20;")
    List<Recipe> getRandomRecipe();

    @Insert("INSERT INTO recipe ( title, des, cats, ing, steps, tip ) " +
            "VALUES (#{title},#{des},#{cats},#{ing},#{steps},#{tip})")
    void addRecipe(Recipe recipe);

    @Select("SELECT * FROM video")
    List<Video> getVideos();

    @Update("UPDATE video set like_count=like_count+1 where id = #{id}")
    void updateLikeCount(String id);


    @Update("UPDATE video set scan_count=scan_count+1 where id = #{id}")
    void updateScanCount(String id);

    @Select("SELECT * FROM recipe where status = 'false'")
    List<Recipe> getReviewRecipes();

    @Update("UPDATE recipe set recipe.`status` = 'true' where recipe.id = #{id}")
    void setReviewRecipes(String id);

    @Delete("delete from recipe WHERE recipe.id  = #{id}")
    void deleteRecipesById(String id);
}
