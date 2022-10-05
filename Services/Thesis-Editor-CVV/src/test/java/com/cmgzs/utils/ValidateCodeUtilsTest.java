package com.cmgzs.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
class ValidateCodeUtilsTest {

    @Test
    void generateValidateCode() {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        log.info("validateCode:{}", validateCode);
    }

    @Test
    void generateValidateCode4String() {
        String generateValidateCode4String = ValidateCodeUtils.generateValidateCode4String(6);
        log.info("generateValidateCode4String:{}", generateValidateCode4String);
    }
}