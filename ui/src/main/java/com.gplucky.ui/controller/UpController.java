package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.constants.Constants;
import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehsy_it on 2017/2/21.
 */
@Controller
@RequestMapping("up")
public class UpController {

    @Autowired
    private TaskService taskHystrixService;

    @RequestMapping("list")
    public String list(Model model,
                       @RequestParam(value = "num", required = false, defaultValue="1") int num,
                       @RequestParam(value = "pageNo", required = false, defaultValue="1") int pageNo){
        String seqUpResultStr = taskHystrixService.getSeqUpByDays(num, pageNo);
        HttpResult result = JSON.parseObject(seqUpResultStr, HttpResult.class);
        List<String> seqUpSet = (List<String>) result.getData();
        int total = null == result.getPage()?0:result.getPage().getTotal();
        model.addAttribute("seqUpSetString", seqUpSet);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalPage", total / Constants.PAGE_SIZE_10);
        model.addAttribute("PageCount", total);
        model.addAttribute("PageSize", Constants.PAGE_SIZE_10);
        model.addAttribute("countindex", pageNo * Constants.PAGE_SIZE_10);
        model.addAttribute("nums", getNumsList());
        model.addAttribute("num", num);
        return "up/list";
    }

    private List<Integer> getNumsList() {
        List<Integer> list = new ArrayList<>();
        for(int i=1; i <=20; i++){
            list.add(i);
        }
        return list;
    }
}
