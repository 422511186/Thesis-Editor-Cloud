package com.cmgzs.consumer;

import com.cmgzs.service.EmailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 验证码发送
 *
 * @author huangzhenyu
 * @date 2022/10/9
 */
@Slf4j
@Component
public class CvvConsumer {

    @Resource
    private EmailService emailService;

    /**
     * 队列不存在时，需要创建一个队列，并且与exchange绑定
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.n1", durable = "false", autoDelete = "true"),
            exchange = @Exchange(value = "emailExchange", type = ExchangeTypes.DIRECT),
            key = "email"),
            ackMode = "MANUAL"
    )
    public void consumerDoAck(String toEmail, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        log.info("验证码收件人:{} ", toEmail);
        try {
            emailService.sendEmail(toEmail);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //  RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费，第三个参数true，表示这个消息会重新进入队列
            channel.basicNack(deliveryTag, false, true);
            e.printStackTrace();
        }
    }
}
