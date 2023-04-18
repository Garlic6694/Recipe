package com.upc.recipe.dao;

import com.upc.recipe.mbg.model.VoteDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class VoteRepository {
    @Autowired
    private JdbcTemplate db;

    /**
     * 添加点赞
     *
     * @param vote 点赞对象
     * @return 如果插入成功，返回true，否则返回false
     */
    public int addVote(/*valid*/ VoteDocument vote) {
        String sql = "insert into vote_document(voter_id,content_id,voting,votes) values(?,?,?)";
        return db.update(sql, vote.getUserId(), vote.getRecipeId(), vote.getVoting());
    }

}
