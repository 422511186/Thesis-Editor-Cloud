package com.cmgzs.controller.params;

import lombok.Data;

/**
 * 用户登录params
 *
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Data
public class LoginParams {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 修改密码 -- 新密码
     */
    private String NewPassWord;
}
