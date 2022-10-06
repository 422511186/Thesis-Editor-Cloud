package com.cmgzs.domain.auth;

import lombok.Data;

/**
 * @author huangzhenyu
 * @date 2022/10/6
 */
@Data
public class Permission {
    /**
     * 权限ID
     */
    private String permissionId;
    /**
     * 权限
     */
    private String value;
}
