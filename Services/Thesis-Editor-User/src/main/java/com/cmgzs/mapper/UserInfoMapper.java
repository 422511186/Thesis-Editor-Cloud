package com.cmgzs.mapper;

import com.cmgzs.domain.auth.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author huangzhenyu
 * @date 2022/10/15
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 通过用户名修改用户信息
     *
     * @param user
     * @return
     */
    int updateUserInfoByUserId(User user);

    /**
     * 通过用户Id获取昵称
     *
     * @param userIds
     * @return
     */
    @MapKey(value = "id")
    Map<String, User> getNickNames(String[] userIds);

}
