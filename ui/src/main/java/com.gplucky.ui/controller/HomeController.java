package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ehsy_it on 2017/3/11.
 */
@Controller
public class HomeController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/home")
    public String home(@ModelAttribute("loginFlg") String loginFlg,
                       Stock stock, PageG page,
                       Model model) {
        if (null == page) page = new PageG();
        ResponseEntity<String> responseEntity = taskService.selectStock(JSON.toJSONString(stock), JSON.toJSONString(page));
        HttpResult result = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        List<Stock> stocks = JSON.parseObject((String) result.getData(), List.class);
        model.addAttribute("page", result.getPageG());
        model.addAttribute("stock", stock);
        model.addAttribute("stocks", stocks);
        model.addAttribute("loginFlg", loginFlg);
        return "home";
    }

}
