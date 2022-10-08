package com.cmgzs.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

/**
 * @author huangzhenyu
 * @date 2022/10/8
 */
public class MailConfig {

    /**
     * 邮箱 交换机
     */
    private static final String E_MAIL_EXCHANGE_NAME = "e_mail-exchange";
    /**
     * 邮箱验证码 队列
     */
    private static final String EMAIL_CVV_QUEUE_NAME = "email-cvv-queue";
    /**
     * 邮箱验证码 RoutingKey
     */
    private static final String EMAIL_ROUTING_KEY = "email-cvv-key";
    /**
     * 备份交换机
     */
    private static final String BACKUP_EXCHANGE_NAME = "backup-exchange";
    /**
     * 备份队列
     */
    private static final String BACKUP_QUEUE_NAME = "backup-queue";

    /**
     * 邮箱交换机
     *
     * @return
     */
    @Bean("emailExchange")
    public DirectExchange directExchange() {
        /**
         * 确认交换机配置备份交换机 以确保宕机后将消息转发到备份交换机
         */
        return ExchangeBuilder.directExchange(E_MAIL_EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }

    /**
     * 邮箱验证码 队列
     *
     * @return
     */
    @Bean("emailQueue")
    public Queue confirmQueue() {
        HashMap<String, Object> map = new HashMap<>(8);
        return new Queue(EMAIL_CVV_QUEUE_NAME, false, false, false, map);
    }

    /**
     * @param queue     邮箱验证码 队列
     * @param exchange  邮箱交换机
     * @return
     */
    @Bean
    public Binding queueConfirmBindingExchange(@Qualifier("emailQueue") Queue queue,
                                               @Qualifier("emailExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(EMAIL_ROUTING_KEY).noargs();
    }

    /**
     * 备份交换机
     *
     * @return
     */
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 备份队列
     *
     * @return
     */
    @Bean("backupQueue")
    public Queue backupQueue() {
        HashMap<String, Object> map = new HashMap<>(8);
        return new Queue(BACKUP_QUEUE_NAME, false, false, false, map);
    }

    /**
     *
     * @param queue 备份队列
     * @param exchange  备份交换机
     * @return
     */
    @Bean
    public Binding backupConfirmBindingExchange(@Qualifier("backupQueue") Queue queue,
                                                @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}
