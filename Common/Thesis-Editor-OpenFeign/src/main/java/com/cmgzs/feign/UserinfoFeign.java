package com.cmgzs.feign;

import com.cmgzs.constant.serverConstants;
import com.cmgzs.domain.base.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangzhenyu
 * @date 2022/10/20
 */
@Component
@FeignClient(value = serverConstants.thesis_editor_user, path = "/userInfo")
public interface UserinfoFeign {

    @RequestMapping(value = "/getNickNames", method = RequestMethod.GET)
    ApiResult getNickNames(@RequestParam(value = "userIds") String[] userIds);
}
