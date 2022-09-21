package com.cmgzs.fein;


import com.cmgzs.domain.base.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(value = "Thesis-Editor-Authorize", path = "auth")
public interface JwtAuthController {


    /**
     * 解析token所对应的用户信息
     *
     * @return
     */
    @GetMapping(value = "/getUser")
    ApiResult getUser();

}
