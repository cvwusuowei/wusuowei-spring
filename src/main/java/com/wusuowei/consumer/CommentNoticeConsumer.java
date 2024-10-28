package com.wusuowei.consumer;

import com.alibaba.fastjson.JSON;
import com.wusuowei.model.dto.EmailDTO;
import com.wusuowei.util.EmailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.wusuowei.constant.RabbitMQConstant.EMAIL_QUEUE;

@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class CommentNoticeConsumer {

    @Autowired
    private EmailUtil emailUtil;

    @RabbitHandler
    public void process(byte[] data) {
        //解析邮件
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        //调用方法发送邮件
        emailUtil.sendHtmlMail(emailDTO);
    }

}
