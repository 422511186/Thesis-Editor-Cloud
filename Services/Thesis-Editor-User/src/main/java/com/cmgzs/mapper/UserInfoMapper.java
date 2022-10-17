package com.cmgzs.mapper;

import com.cmgzs.domain.auth.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author huangzhenyu
 * @date 2022/10/15
 */
@Mapper
public interface UserInfoMapper {

    /**
     *
     *通过用户名修改用户信息
     *
     * @param user
     * @return
     */
    int updateUserInfoByUserId(User user);
}
