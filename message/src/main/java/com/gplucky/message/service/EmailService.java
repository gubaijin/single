package com.gplucky.message.service;

import java.io.File;
import java.util.Map;

/**
 * Created by ehsy_it on 2017/3/23.
 */
public interface EmailService {

    /**
     * 发送简单邮件
     * @param sendTo 收件人地址
     * @param title  邮件标题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String sendTo, String title, String content);

    /**
     * 发送简单邮件
     * @param sendTo 收件人地址
     * @param title  邮件标题
     * @param content 邮件内容
     * @param attachments<文件名，附件> 附件列表
     */
    public void sendAttachmentsMail(String sendTo, String title, String content, Map<String, File> attachments);

}
