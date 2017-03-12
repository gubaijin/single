package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/3/11.
 */
@Controller
public class HomeController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/home")
    public String home(@ModelAttribute("loginFlg") String loginFlg,
                       @RequestParam(value = "stock", required = false) Stock stock,
                       @RequestParam(value = "page", required = false) PageG page,
                       Model model) {
        if(null == page) page = new PageG();
        System.out.println(JSON.toJSONString(stock));
        System.out.println(JSON.toJSONString(page));
        taskService.select(JSON.toJSONString(stock), JSON.toJSONString(page));
        model.addAttribute("loginFlg", loginFlg);
        return "home";
    }
}
