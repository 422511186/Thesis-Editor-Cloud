package com.cmgzs.service;

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
    int updateUserInfo();

}
