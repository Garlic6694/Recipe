package com.upc.recipe.component;

import com.upc.recipe.common.collections.UnprocessedMessage;
import com.upc.recipe.common.factory.SingletonFactory;
import com.upc.recipe.mbg.model.Recipe;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import com.upc.recipe.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class VoteInDBTask {

    private final UnprocessedMessage unprocessedMessage = SingletonFactory.getInstance(UnprocessedMessage.class);

    @Autowired
    private VoteService voteService;

    @Scheduled(cron = "0/30 * * * * ?")
    private void cancelTimeOutOrder() {
        writeVDIntoDB();
        writeRecipeVotesIntoDB();

        unprocessedMessage.clear();
    }

    public void writeVDIntoDB() {
        List<VoteDocument> voteDocumentList = unprocessedMessage.getHistoryList();
        if (voteDocumentList.size() > 0) {
            voteService.insertUserVoteList(voteDocumentList);
            log.info("点赞消息入库成功");
        }
    }

    public void writeRecipeVotesIntoDB() {
        List<VoteRecipe> voteRecipeList = unprocessedMessage.getRecipeVoteList();
        if (voteRecipeList.size() > 0) {
            voteService.insertRecipeVoteList(voteRecipeList);
            log.info("菜谱总点赞消息入库成功");
        }
    }
}
