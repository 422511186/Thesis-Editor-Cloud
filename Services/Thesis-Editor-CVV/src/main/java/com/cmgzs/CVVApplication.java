package com.cmgzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CVVApplication {
    public static void main(String[] args) {
        SpringApplication.run(CVVApplication.class, args);
    }
}
