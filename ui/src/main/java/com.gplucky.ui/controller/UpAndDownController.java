package com.gplucky.ui.controller;

import com.alibaba.fastjson.JSON;
import com.gplucky.common.bean.HttpResult;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.bean.UpAndDown;
import com.gplucky.common.constants.Constants;
import com.gplucky.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehsy_it on 2017/2/21.
 */
@Controller
@RequestMapping("upAndDown")
public class UpAndDownController {

    @Autowired
    private TaskService taskHystrixService;

    @RequestMapping("list")
    public String list(Model model, UpAndDown upAndDown, PageG page) {
        ResponseEntity<String> responseEntity = taskHystrixService.getSeqUpByDays(page.getPageNo(), JSON.toJSONString(upAndDown));

        HttpResult result = JSON.parseObject(responseEntity.getBody(), HttpResult.class);
        List<String> seqUpSet = (List<String>) result.getData();
        int total = null == result.getPageG() ? 0 : (int) result.getPageG().getCount();
        model.addAttribute("seqUpSetString", seqUpSet);
        page = result.getPageG();
        page.setTotalPage(total % Constants.PAGE_SIZE_10 == 0 ? total / Constants.PAGE_SIZE_10 : (total / Constants.PAGE_SIZE_10) + 1);
        model.addAttribute("page", page);
        model.addAttribute("nums", getNumsList());
        model.addAttribute("upAndDown", upAndDown);
        return "seqUpAndDown";
    }

    private List<Integer> getNumsList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(i);
        }
        return list;
    }
}
