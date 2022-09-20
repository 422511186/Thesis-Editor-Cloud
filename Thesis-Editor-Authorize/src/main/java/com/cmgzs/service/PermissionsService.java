package com.cmgzs.service;


import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface PermissionsService {
    /**
     * 获取当前账号对应的权限列表
     *
     * @return 结果
     */
    List<GrantedAuthority> getPermissions(String id);
}
