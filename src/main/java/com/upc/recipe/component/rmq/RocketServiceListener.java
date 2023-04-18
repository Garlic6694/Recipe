package com.upc.recipe.component.rmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upc.recipe.common.collections.UnprocessedMessage;
import com.upc.recipe.common.factory.SingletonFactory;
import com.upc.recipe.mbg.model.VoteDocument;
import com.upc.recipe.mbg.model.VoteRecipe;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class RocketServiceListener {

    private final UnprocessedMessage unprocessedMessage = SingletonFactory.getInstance(UnprocessedMessage.class);

    @Service
    @RocketMQMessageListener(consumerGroup = "consumer-group-1", topic = "rocket-topic-2")
    public class Consumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            JSONObject resultJson = JSON.parseObject(s);
            VoteDocument voteDocument = resultJson.getObject("voteDoc", VoteDocument.class);
            VoteRecipe voteRecipe = resultJson.getObject("voteRecipe", VoteRecipe.class);

            unprocessedMessage.addUserVotes(voteDocument);
            unprocessedMessage.addRecipeVotes(voteRecipe);

            log.info("consumer1 rocket收到消息：{}", s);
        }
    }

    // RocketMQ支持两种消费方式，集器消费和广播消费
    @Service
    @RocketMQMessageListener(consumerGroup = "consumer-group-2", topic = "rocket-topic-2",
            selectorExpression = "tag2", messageModel = MessageModel.BROADCASTING)
    public class Consumer2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            log.info("consumer2 rocket收到消息：{}", s);
        }
    }
}