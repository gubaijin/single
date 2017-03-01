package com.gplucky.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ehsy_it on 2017/3/1.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String toIndex() {
        return "forward:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
