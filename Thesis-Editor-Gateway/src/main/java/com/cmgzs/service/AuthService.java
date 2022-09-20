package com.cmgzs.service;

//import com.cmgzs.config.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author huangzhenyu
 * @date 2022/9/20
 */
@Component
@FeignClient(value = "Thesis-Editor-authenticate", path = "auth")
public interface AuthService {

    /**
     * 获取当前账户权限列表
     *
     * @return
     */
    @PostMapping("/isLegal/{token}")
    Object isLegal(@PathVariable("token") String token);
}
