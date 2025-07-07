package org.example.vtils.mail;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Test;

import javax.mail.MessagingException;

public class MailSenderTest {

    @Test
    public void testSend() throws MessagingException {
        Dotenv dotenv = Dotenv.load();
        MailSender mailSender = MailSender.builder()
                .host(dotenv.get("HOST"))
                .port(dotenv.get("PORT"))
                .username(dotenv.get("USERNAME"))
                .password(dotenv.get("PASSWORD"))
                .build();
        mailSender.send("lkzc19@foxmail.com", "测试", "一个测试");
    }
}