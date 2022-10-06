package com.cmgzs.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取当前用户信息的辅助类`
 *
 * @author hzy
 * @date 2022/9/21
 */
public class UserContext {

    private static ThreadLocal<Map<String, Object>> threadLocal;

    /**
     * tokenKey
     */
    public static final String CONTEXT_KEY_USER_TOKEN = "token";
    /**
     * 用户idKey
     */
    public static final String CONTEXT_KEY_USER_ID = "userId";
    /**
     * 用户名
     */
    public static final String CONTEXT_KEY_USER_NAME = "userName";

    /**
     * 角色
     */
    public static final String CONTEXT_KEY_ROLES_NAME = "roles";

    /**
     * 权限
     */
    public static final String CONTEXT_KEY_PERMISSIONS_NAME = "permissions";


    static {
        threadLocal = new ThreadLocal<>();
    }

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 获取数据
     *
     * @return 值
     */
    public static Object get() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(6);

        }
        return map;
    }

    /**
     * 清除数据
     */
    public static void remove() {
        threadLocal.remove();
    }

    public static void setUserId(String userId) {
        set(CONTEXT_KEY_USER_ID, userId);
    }


    public static void setUserName(String userName) {
        set(CONTEXT_KEY_USER_NAME, userName);
    }

    public static void setToken(String token) {
        set(CONTEXT_KEY_USER_TOKEN, token);
    }

    public static void setRoles(List<String> roles) {
        set(CONTEXT_KEY_ROLES_NAME, roles);
    }

    public static void setPermissions(List<String> permissions) {
        set(CONTEXT_KEY_PERMISSIONS_NAME, permissions);
    }

    public static String getUserId() {
        Object value = get(CONTEXT_KEY_USER_ID);
        return String.valueOf(value);
    }

    public static String getUserName() {
        Object value = get(CONTEXT_KEY_USER_NAME);
        return String.valueOf(value);
    }

    public static String getToken() {
        Object value = get(CONTEXT_KEY_USER_TOKEN);
        return String.valueOf(value);
    }

    public static List<String> getRoles() {
        Object value = get(CONTEXT_KEY_ROLES_NAME);
        return castList(value, String.class);
    }

    public static List<String> getPermissions() {
        Object value = get(CONTEXT_KEY_PERMISSIONS_NAME);
        return castList(value, String.class);
    }

    /**
     * object对象转换List类型
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}