package com.cmgzs.service;

import com.cmgzs.domain.auth.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户信息模块
 *
 * @author huangzhenyu
 * @date 2022/9/30
 */
public interface UserInfoService {
    /**
     * 上传头像
     */
    int uploadImage();

    /**
     * 用户信息更新
     */
    int updateUserInfo(User user);

    Map<String, User> getNickNames(String[] userIds);
}
