package com.ziorye.proofread.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class IndexController {
    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    /**
     * email
     * <img src="https://xpicx.oss-cn-shenzhen.aliyuncs.com/uPic/send-email-2.png" alt="send-email">
     *
     * @return template name
     */
    @GetMapping("/send-mail")
    @ResponseBody
    public String send() throws MessagingException, UnsupportedEncodingException {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("localhost");
        sender.setPort(1025);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(new InternetAddress("admin@example.com", "Admin"));
        helper.setSubject("Hello, world!");
        helper.setTo("user@example.com");
        helper.setText("Thank you for ordering!");

        sender.send(message);

        return "ok";
    }
}
