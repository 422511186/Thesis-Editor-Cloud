package com.cmgzs.domain;

import com.cmgzs.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户实体类
 */
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    //    主键
    @JsonIgnore
    private int id;
    //    用户名
    @NotBlank(message = "用户名不能为空")
    private String userName;
    //    密码
    @NotBlank(message = "密码不能为空")
//    @Length(min = 6, max = 16)
    private String passWord;
}
