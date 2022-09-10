package com.cmgzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PermissionsMapper {
    /**
     * 获取当前账号对应的权限列表
     *
     * @return 结果
     */
    List<String> getPermissions(@Param(value = "user_id") int id);
}
