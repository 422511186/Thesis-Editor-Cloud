package com.cmgzs.utils;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailUtilsTest {

    @Autowired
    private EmailUtils emailUtils;


    @Test
    public void sendMessage() {
        emailUtils.sendMessage("2658096972@qq.com", "service",
                "尊敬的陶先生，恭喜你已被我校--嘉丽顿学院录取，你的专业为：养猪专业，请在10.15前到校报道，过期不候！");
    }

}