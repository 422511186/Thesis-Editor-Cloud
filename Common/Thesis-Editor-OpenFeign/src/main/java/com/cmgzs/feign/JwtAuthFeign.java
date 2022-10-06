package com.cmgzs.feign;


import com.cmgzs.constant.serverConstants;
import com.cmgzs.domain.base.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(value = serverConstants.thesis_editor_authorize, path = "auth")
public interface JwtAuthFeign {

    /**
     * 解析token所对应的用户信息
     *
     * @return
     */
    @GetMapping(value = "/getUser")
    ApiResult getUser();

}
