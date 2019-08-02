package com.ls.elk.kafka;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 〈一句话功能简述〉<br>
 * 〈kafka 〉
 *
 * @author liusheng
 * @create 2019/8/2
 * @since 1.0.0
 */
@Component
@Slf4j
public class KafkaSender<T> {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * kafka 发送消息
     *
     * @param obj 消息对象
     */
    public void send(T obj) {
        String jsonObj = JSON.toJSONString(obj);
        log.info("------------ message = {}", jsonObj);
        // 发送消息 实现可配置化 主题是可配置化
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("goods_mylog", jsonObj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("Produce: The message failed to be sent:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                // TODO 业务处理
                log.info("Produce: The message was sent successfully:");
                log.info("Produce: _+_+_+_+_+_+_+ result: " + stringObjectSendResult.toString());
            }
        });
    }
}
