package com.cmgzs.controller;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * redisson测试
 *
 * @author huangzhenyu
 * @date 2022/9/24
 */
@Slf4j
@RestController
@RequestMapping("/auth/redis")
public class RedissionApiController {

    @Autowired
    private RedissonService redissonService;

    @GetMapping("/test")
    public ApiResult test() {
        String key = "test_key1";
        try {
            log.info("============={} 线程访问开始============", Thread.currentThread().getName());
            //TODO 尝试获取锁，等待1秒，自己获得锁后一直不解锁则30秒后自动解锁
            boolean lock = redissonService.tryLock(key, TimeUnit.SECONDS, 1L, 30L);
            if (lock) {
                log.info("线程:{}，获取到了锁", Thread.currentThread().getName());
                //TODO 获得锁后，进行业务处理
                Thread.sleep(3000);
                log.info("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
            } else {
                throw new RuntimeException("获取锁失败");
            }
        } catch (Exception e) {
            log.info("错误信息：{}", e.toString());
            log.info("线程：{} 获取锁失败", Thread.currentThread().getName());
            return ApiResult.error("获取锁失败");
        } finally {
            redissonService.unlock(key);
        }
        return ApiResult.success();
    }
}