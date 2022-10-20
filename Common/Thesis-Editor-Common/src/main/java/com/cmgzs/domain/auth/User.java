package com.cmgzs.domain.auth;

import com.cmgzs.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {


    private String id;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户性别
     * 0：未知 1:男 2：女
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 权限
     */
    private List<String> permissions;
}
