package com.gplucky.parameter.feign;

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
public interface TaskFegin {

    @RequestMapping(method = RequestMethod.POST, value = "/stock/selectAll")
    ResponseEntity<String> selectAll(@RequestParam(value = "stock") String stock);
}
