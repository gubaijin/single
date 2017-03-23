package com.gplucky.message.controller;

import com.gplucky.common.controller.BaseController;
import com.gplucky.message.service.EmailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ehsy_it on 2017/1/28.
 */
@Controller
@RequestMapping("mail")
public class MailController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "发送简单邮件", notes = "发送简单邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "sendTo", value = "收件人", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "content", value = "内容", required = true, dataType = "String")
    })
    @RequestMapping(value = "sendSimpleMail", method = RequestMethod.POST)
    public @ResponseBody String sendSimpleMail(
            @RequestParam(value = "sendTo", required = false) String sendTo,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content) {
        try {
            LOG.info("准备发送邮件…………[sendTo={}],[title={}],[content={}]", sendTo, title, content);
            emailService.sendSimpleMail(sendTo, title, content);
            LOG.info("…………邮件发送完成。");
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }

}
