//package com.cmgzs.config;
//
//import com.cmgzs.utils.text.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.config.SingleServerConfig;
//import org.redisson.spring.cache.RedissonSpringCacheManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * redis配置
// *
// * @author hzy
// */
//@Slf4j
//@Configuration
//@EnableCaching
//@ComponentScan
//public class RedisConfig extends CachingConfigurerSupport {
//
//
//    @Value("${properties.redis.host}")
//    private String host;
//
//    @Value("${properties.redis.port}")
//    private String port;
//
//    @Value("${properties.redis.password}")
//    private String password;
//
//    @Value("${properties.redis.minimumIdleSize}")
//    private int minimumIdleSize;
//
//    @Value("${properties.redis.connectionPoolSize}")
//    private int connectionPoolSize;
//
//    @Value("${properties.redis.database}")
//    private int database;
//
//
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        RedisSerializerImpl serializer = new RedisSerializerImpl(Object.class);
//
//        // 使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(serializer);
//
//        // Hash的key也采用StringRedisSerializer的序列化方式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean(destroyMethod = "shutdown", name = "redissonClient")
//    RedissonClient redisson() throws IOException {
//        Config config = new Config();
//        log.info(host);
//        log.info(port);
//        SingleServerConfig serverConfig = config.useSingleServer()
//                .setAddress("redis://" + host + ":" + port)
//                .setKeepAlive(true)
//                .setDatabase(database)
//                .setConnectionMinimumIdleSize(minimumIdleSize)
//                .setDnsMonitoringInterval(30000L)
//                .setConnectionPoolSize(connectionPoolSize);
//        if (StringUtils.isNotBlank(password)) {
//            serverConfig.setPassword(password);
//        }
//        return Redisson.create(config);
//    }
//
//    @Bean
//    CacheManager cacheManager(@Qualifier("redissonClient") RedissonClient redissonClient) {
//        Map config = new HashMap();
//        return new RedissonSpringCacheManager(redissonClient, config);
//    }
//}
