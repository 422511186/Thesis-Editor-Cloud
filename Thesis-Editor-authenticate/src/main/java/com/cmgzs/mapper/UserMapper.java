package com.cmgzs.mapper;


import com.cmgzs.domain.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {

    /**
     * 通过用户名查找账号信息
     *
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 插入用户信息
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    int updateUser(User user);
}
