package com.cmgzs.domain.auth;

import lombok.Data;

/**
 * @author huangzhenyu
 * @date 2022/10/6
 */
@Data
public class Role {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
