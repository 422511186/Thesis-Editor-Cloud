package com.cmgzs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * @author huangzhenyu
 * @date 2022/9/25
 */
@Configuration
public class MongodbConfig {

    /**
     * mongodb的事务管理器
     *
     * @param factory
     * @return 结果
     */
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory factory) {
        return new MongoTransactionManager(factory);
    }


}