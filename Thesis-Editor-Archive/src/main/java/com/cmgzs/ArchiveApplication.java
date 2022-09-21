package com.cmgzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author huangzhenyu
 * @date 2022/9/10
 */

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ArchiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArchiveApplication.class, args);
    }
}
