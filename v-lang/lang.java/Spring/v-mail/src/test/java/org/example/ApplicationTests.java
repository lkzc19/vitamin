package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom("drinkice_cat@foxmail.com");
        //邮件接收人
        message.setTo("lkzc19@foxmail.com", "drinkice_cat@foxmail.com");
        //邮件主题
        message.setSubject("说一段神话");
        //邮件内容
        message.setText(
                "说一段神话" +
                        "话说那么一家" +
                        "这家夫妻俩" +
                        "生了个怪娃娃" +
                        "扎俩个冲天鬏" +
                        "光着俩小脚丫" +
                        "踩着俩风火轮" +
                        "乾坤圈手中拿" +
                        "混天绫护着他" +
                        "轩辕箭满弓拉" +
                        "两眼是照妖镜" +
                        "双腿是追风马" +
                        "乾坤圈伴着他" +
                        "上天下海本事大" +
                        "三头六臂显威力" +
                        "千征百战斗魔法" +
                        "要问他名字叫什么" +
                        "哪吒 哪吒 小哪吒"
        );
        //发送邮件
        mailSender.send(message);
    }

    @Test
    void sendHtmlEmailByThymeleaf() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        //工具类MimeMessageHelper用于配置邮件的属性
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("drinkice_cat@foxmail.com");
        helper.setTo("lkzc19@foxmail.com");
        helper.setSubject("验证码");

        //设置模板中的变量
        Context context = new Context();
        context.setVariable("username","lkzc19");
        context.setVariable("vcode","9527");
        String value = templateEngine.process("vcode.html", context);
        helper.setText(value,true);//true表示发送HTML格式的邮件。
        mailSender.send(mimeMessage);
    }

    @Test
    public void sendHtmlEmailByFreemarker() throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("drinkice_cat@foxmail.com");
        helper.setTo("lkzc19@foxmail.com");
        helper.setSubject("验证码");

        // 处理 FTL 模板
        Configuration cfg = freeMarkerConfigurer.getConfiguration();
        Template template = cfg.getTemplate("vcode.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("username","lkzc19");
        model.put("vcode","9527");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setText(html, true); // true 表示 HTML 格式
        mailSender.send(mimeMessage);
    }
}
