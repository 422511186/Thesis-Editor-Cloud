package com.cmgzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangzhenyu
 * @date 2022/9/20
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthorizeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizeApplication.class, args);
    }
}
