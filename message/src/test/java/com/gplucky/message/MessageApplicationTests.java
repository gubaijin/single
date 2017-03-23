package com.gplucky.message;

import com.gplucky.message.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void test() {
        String sendTo = "gplucky@126.com";
        String titel = "gplucky测试邮件标题";
        String content = "golucky测试邮件内容";
        emailService.sendSimpleMail(sendTo, titel, content);
    }

}
