package com.cmgzs.service.impl;

import com.cmgzs.domain.UserContext;
import com.cmgzs.domain.auth.User;
import com.cmgzs.mapper.UserInfoMapper;
import com.cmgzs.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author huangzhenyu
 * @date 2022/10/15
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    /**
     * 上传头像
     */
    @Override
    public int uploadImage() {
        return 0;
    }

    /**
     * 用户信息更新
     *
     * @param user
     */
    @Override
    public int updateUserInfo(User user) {
        user.setId(UserContext.getUserId());
        return userInfoMapper.updateUserInfoByUserId(user);
    }

    @Override
    public Map<String, User> getNickNames(String[] userIds) {
        return userInfoMapper.getNickNames(userIds);
    }
}
