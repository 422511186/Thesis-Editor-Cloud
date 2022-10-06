package com.cmgzs.domain.cvv.params;

import lombok.Data;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Data
public class VerifyCodeParams {

    /**
     * 验证码 键
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;
}
