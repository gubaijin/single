package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.mybatis.model.StockParams;
import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by ehsy_it on 2017/2/21.
 */
@Controller
@RequestMapping("params")
public class ParamsController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("list")
    public String list(Model model, StockParams stockParams, PageG page) {
        ResponseEntity<String> responseEntity = taskService.selectParams(JSON.toJSONString(stockParams), JSON.toJSONString(page));

        HttpResult result = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        List<StockParams> stockParamsList = JSON.parseObject((String) result.getData(), List.class);
        model.addAttribute("page", result.getPageG());
        model.addAttribute("stockParams", stockParams);
        model.addAttribute("stockParamsList", stockParamsList);
        return "params";
    }
}
