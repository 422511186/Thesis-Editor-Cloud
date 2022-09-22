package com.cmgzs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.util.Md5Utils;
import com.cmgzs.filter.HijackedGatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author huangzhenyu
 * @date 2022/9/22
 */
@Slf4j
public class AccessRequestCheck {
    private static final String ERROR_MESSAGE = "拒绝服务";

    public static void checkSign(String sign, Long dateTimestamp, String requestId, Map<String, Object> paramMap) {

        String str = JSON.toJSONString(paramMap) + requestId + dateTimestamp;
        String tempSign = Md5Utils.getMD5(str.getBytes());
        if (!tempSign.equals(sign)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    /**
     * 修改前端传的参数
     */
    public static Map<String, Object> updateRequestParam(ServerWebExchange exchange) throws NoSuchFieldException, IllegalAccessException {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String query = uri.getQuery();
        if (StringUtils.isNotBlank(query) && query.contains("params")) {
            String[] split = query.split("=");
            String param = RSAUtils.decrypt(split[1], HijackedGatewayFilter.PRIVATE_KEY);
            Field targetQuery = uri.getClass().getDeclaredField("query");
            targetQuery.setAccessible(true);
            targetQuery.set(uri, param);
            return getParamMap(param);
        }
        return new TreeMap<>();
    }


    public static Map<String, Object> getParamMap(String param) {
        Map<String, Object> map = new TreeMap<>();
        if (param == null || "".equals(param))
            return map;
        String[] split = param.split("&");
        for (String str : split) {
            String[] params = str.split("=");
            if (params.length < 2)
                return map;
            map.put(params[0], params[1]);
        }
        return map;
    }


    public static String getSign(HttpHeaders headers) {
        List<String> list = headers.get("sign");
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return list.get(0);
    }

    public static Long getDateTimestamp(HttpHeaders httpHeaders) {
        List<String> list = httpHeaders.get("timestamp");
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        long timestamp = Long.parseLong(list.get(0));
        long currentTimeMillis = System.currentTimeMillis();
        //有效时长为5分钟
//        if (currentTimeMillis - timestamp > 1000 * 60 * 5) {
//            throw new IllegalArgumentException(ERROR_MESSAGE);
//        }
        return timestamp;
    }

    public static String getRequestId(HttpHeaders headers) {
        List<String> list = headers.get("requestId");
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        String requestId = list.get(0);
//        //如果requestId存在redis中直接返回
//        String temp = redisTemplate.opsForValue().get(requestId);
//        if (temp != null) {
//            throw new IllegalArgumentException(ERROR_MESSAGE);
//        }
//        redisTemplate.opsForValue().set(requestId, requestId, 5, TimeUnit.MINUTES);
        return requestId;
    }

}
