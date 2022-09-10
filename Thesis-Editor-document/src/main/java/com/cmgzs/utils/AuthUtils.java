package com.cmgzs.utils;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.exception.CustomException;
import com.cmgzs.utils.request.ServletUtils;
import com.cmgzs.utils.token.TokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/9/10
 */
@Component
public class AuthUtils {
    @Resource
    private RedisTemplate redisTemplate;

    public String getUserName() {
        JSONObject object = getAllInfo();
        return String.valueOf(object.get("username"));
    }

    public JSONObject getUser() {
        JSONObject object = getAllInfo();
        return object.getJSONObject("user");
    }

    public JSONObject getAllInfo() {
        String userKey = TokenUtils.getUserKey(ServletUtils.getRequest());
        assert userKey != null;
        Object o = redisTemplate.opsForValue().get(userKey);
        if (o == null) {
            throw new CustomException("无效token");
        }
        return JSONObject.parseObject(JSONObject.toJSONString(o));
    }

}
