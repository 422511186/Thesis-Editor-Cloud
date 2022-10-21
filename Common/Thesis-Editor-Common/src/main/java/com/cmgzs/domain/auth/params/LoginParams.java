package com.cmgzs.domain.auth.params;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 用户登录params
 *
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Data
public class LoginParams {
    public interface Insert{

    }

    public interface Update{

    }
    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String code;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String passWord;

    /**
     * 修改密码 -- 新密码
     */
    @NotNull(message = "密码不能为空",groups = Update.class)
    private String NewPassWord;

    /**
     * 用户邮箱
     */
    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;
}
