package com.gplucky.message.service.impl;

import com.gplucky.common.exception.CMRuntimeException;
import com.gplucky.message.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

import static com.gplucky.common.exception.ResultCode.CODE_ERROR_EMAIL_ATTACHMENT;

/**
 * Created by ehsy_it on 2017/3/23.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String sendTo, String titel, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(sendTo);
        message.setSubject(titel);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendAttachmentsMail(String sendTo, String titel, String content, Map<String, File> attachments) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject(titel);
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
