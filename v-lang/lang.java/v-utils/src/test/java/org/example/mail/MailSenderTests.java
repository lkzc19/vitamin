package org.example.mail;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Test;

import javax.mail.MessagingException;

public class MailSenderTests {

    @Test
    public void send() throws MessagingException {
        Dotenv dotenv = Dotenv.load();
        MailSender mailSender = MailSender.builder()
                .host(dotenv.get("HOST"))
                .port(dotenv.get("PORT"))
                .username(dotenv.get("USERNAME"))
                .password(dotenv.get("PASSWORD"))
                .build();
        mailSender.send("lkzc19@foxmail.com", "测试", "I am nahida's dog");
    }
}
