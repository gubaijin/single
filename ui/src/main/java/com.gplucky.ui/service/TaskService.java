package com.gplucky.ui.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@FeignClient(value = "task", fallback = TaskHystrixService.class)
public interface TaskService {

    @RequestMapping(method = RequestMethod.GET, value = "/redis/getSeqUpByDays")
    String getSeqUpByDays(@RequestParam(value = "num") Integer num,
                          @RequestParam(value = "pageNo") Integer pageNo);

}
