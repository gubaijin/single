package com.gplucky.message.service.impl;

import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.message.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Properties;

import static com.gplucky.common.exception.ResultCode.CODE_ERROR_EMAIL_ATTACHMENT;

/**
 * Created by ehsy_it on 2017/3/23.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendSimpleMail(String sendTo, String title, String content) {
        mailSsl();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (MailException e) {
            mailSender.send(message);
        }
    }

    private void mailSsl() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");              // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(prop);
    }

    public void sendAttachmentsMail(String sendTo, String title, String content, Map<String, File> attachments) {
        mailSsl();
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);
            for (Map.Entry<String, File> attachment : attachments.entrySet()) {
                helper.addAttachment(attachment.getKey(), new FileSystemResource(attachment.getValue()));
            }
        } catch (Exception e) {
            throw new CMRuntimeException(CODE_ERROR_EMAIL_ATTACHMENT.getCode(), CODE_ERROR_EMAIL_ATTACHMENT.getMsg());
        }
        mailSender.send(mimeMessage);
    }

}
