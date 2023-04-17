package com.upc.recipe.service.impl;

import com.upc.recipe.dao.EsRecipeDao;
import com.upc.recipe.nosql.elasticsearch.document.EsRecipe;
import com.upc.recipe.nosql.elasticsearch.repository.EsRecipeRepository;
import com.upc.recipe.service.EsRecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class EsRecipeServiceImpl implements EsRecipeService {

    @Autowired
    private EsRecipeDao recipeDao;
    @Autowired
    private EsRecipeRepository esRecipeRepository;

    /**
     * 从数据库中导入所有菜谱到ES
     */
    @Override
    public int importAll() {
        List<EsRecipe> esProductList = recipeDao.getAllEsRecipeList(null);
        Iterable<EsRecipe> esProductIterable = esRecipeRepository.saveAll(esProductList);
        Iterator<EsRecipe> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    /**
     * 根据id删除菜谱
     *
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        esRecipeRepository.deleteById(id);
        return true;
    }

    /**
     * 根据id创建菜谱
     */
    @Override
    public EsRecipe create(Integer id) {
        EsRecipe recipe = null;
        List<EsRecipe> esRecipeList = recipeDao.getAllEsRecipeList(id);
        if (esRecipeList.size() > 0) {
            EsRecipe esRecipe = esRecipeList.get(0);
            recipe = esRecipeRepository.save(esRecipe);
        }
        return recipe;
    }

    /**
     * 批量删除菜谱
     *
     * @return
     */
    @Override
    public boolean delete(List<Integer> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsRecipe> esRecipeList = new ArrayList<>();
            for (Integer id : ids) {
                EsRecipe esRecipe = EsRecipe.builder()
                        .id(id)
                        .build();
                esRecipeList.add(esRecipe);
            }
            esRecipeRepository.deleteAll(esRecipeList);
            return true;
        }
        return false;
    }


    /**
     * 根据关键字搜索名称或者描述或者步骤
     *
     * @param keyword  关键字
     * @param pageNum  页码
     * @param pageSize 每页多少
     * @return 分页
     */
    @Override
    public Page<EsRecipe> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esRecipeRepository.findByTitleOrDesOrSteps(keyword, keyword, keyword, pageable);
    }
}
