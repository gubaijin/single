package com.gplucky.ui.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
//@FeignClient(value = "task", fallback = TaskHystrixService.class)
@FeignClient(value = "task")
public interface TaskService {

    @RequestMapping(method = RequestMethod.GET, value = "/redis/getSeqUpByDays")
    ResponseEntity<String> getSeqUpByDays(@RequestParam(value = "num") Integer num,
                          @RequestParam(value = "pageNo") Integer pageNo,
                          @RequestParam(value = "filterParameters") String filterParameters);

    @RequestMapping(method = RequestMethod.POST, value = "/stock/select")
    ResponseEntity<String> select(@RequestParam(value = "stock") String stock,
                                  @RequestParam(value = "page") String page);
}
