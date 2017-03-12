package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.controller.BaseController;
import com.gplucky.common.mybatis.model.User;
import com.gplucky.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by ehsy_it on 2017/3/1.
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String toIndex() {
        return "forward:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/page1")
    public String page1() {
        return "page1";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("registerFlg") String registerFlg,
                        @ModelAttribute("errorMsg") String errorMsg,
                        Model model) {
        model.addAttribute("registerFlg", registerFlg);
        model.addAttribute("errorMsg", errorMsg);
        return "login";
    }

    @RequestMapping("/postLogin")
    public String postLogin( User user, RedirectAttributes attr) {
        ResponseEntity<String> responseEntity = userService.login(JSON.toJSONString(user));
        HttpResult httpResult = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        String mark = httpResult.getMark();
        if(BaseController.SUCCESS_MARK.equals(mark)){
            attr.addFlashAttribute("loginFlg", "first");
            return "redirect:/home";
        }else{
            attr.addFlashAttribute("errorMsg", httpResult.getMessage());
            return "redirect:/login";
        }
    }

    @RequestMapping("/register")
    public String register( User user ) {
        return "register";
    }

    @RequestMapping("/postRegister")
    public String postRegister( User user, RedirectAttributes attr) {
        ResponseEntity<String> responseEntity = userService.register(JSON.toJSONString(user));
        HttpResult httpResult = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        String mark = httpResult.getMark();
        if(BaseController.SUCCESS_MARK.equals(mark)){
            attr.addFlashAttribute("registerFlg", "success");
            return "redirect:/login";
        }else{
            return "redirect:/register";
        }
    }

    @RequestMapping("/isExistUsername")
    @ResponseBody
    public boolean isExistUsername( String username ) {
        if(StringUtils.isEmpty(username)){
            return false;
        }
        ResponseEntity<String> responseEntity = userService.isExistUsername(username.trim());
        HttpResult httpResult = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        String mark = httpResult.getMark();
        if(BaseController.SUCCESS_MARK.equals(mark)){
            return !(boolean) httpResult.getData();
        }else{
            return false;
        }
    }

    @RequestMapping("/404")
    public String error404() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String error500() {
        return "error/500";
    }
}
