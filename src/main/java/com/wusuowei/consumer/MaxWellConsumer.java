package com.wusuowei.consumer;

import com.alibaba.fastjson.JSON;
import com.wusuowei.entity.Article;
import com.wusuowei.strategy.ElasticsearchMapper;
import com.wusuowei.model.dto.ArticleSearchDTO;
import com.wusuowei.model.dto.MaxwellDataDTO;
import com.wusuowei.util.BeanCopyUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.wusuowei.constant.RabbitMQConstant.MAXWELL_QUEUE;

@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Autowired
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        System.err.println(maxwellDataDTO.getType());
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearchDTO.class));
                System.err.println("update");
                break;
            case "delete":
                elasticsearchMapper.deleteById(article.getId());
                break;
            default:
                break;
        }
    }
}