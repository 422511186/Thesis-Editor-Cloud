package com.cmgzs.vo;

import lombok.Data;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Data
public class VerifyCodeVo {

    /**
     * 键
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;
}
