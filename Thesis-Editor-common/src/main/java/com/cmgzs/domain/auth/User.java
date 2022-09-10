package com.cmgzs.domain.auth;

import com.cmgzs.domain.base.BaseEntity;
import lombok.Data;


/**
 * 用户实体类
 */
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private int id;

    private String userName;

    private String passWord;

    private String newPassWord;
}
