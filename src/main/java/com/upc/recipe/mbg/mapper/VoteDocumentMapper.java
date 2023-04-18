package com.upc.recipe.mbg.mapper;

import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteDocumentMapper {
    long countByExample(VoteDocumentExample example);

    int deleteByExample(VoteDocumentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteDocument record);

    int insertSelective(VoteDocument record);

    List<VoteDocument> selectByExample(VoteDocumentExample example);

    VoteDocument selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteDocument record, @Param("example") VoteDocumentExample example);

    int updateByExample(@Param("record") VoteDocument record, @Param("example") VoteDocumentExample example);

    int updateByPrimaryKeySelective(VoteDocument record);

    int updateByPrimaryKey(VoteDocument record);
}