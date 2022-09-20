package com.cmgzs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author huangzhenyu
 * @date 2022/9/20
 */
@Slf4j
@Component
public class AuthClient {
    @Lazy
    @Resource
    private AuthService authService;

    @Async
    public Future<Object> isLegal(String token) {
        return new AsyncResult<>(authService.isLegal(token));
    }
}
