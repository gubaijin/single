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
    ResponseEntity<String> getSeqUpByDays(@RequestParam(value = "pageNo") Integer pageNo,
                                          @RequestParam(value = "upAndDown") String upAndDown);

    @RequestMapping(method = RequestMethod.POST, value = "/stock/select")
    ResponseEntity<String> selectStock(@RequestParam(value = "stock") String stock,
                                  @RequestParam(value = "page") String page );

    @RequestMapping(method = RequestMethod.POST, value = "/params/select")
    ResponseEntity<String> selectParams(@RequestParam(value = "stockParams") String stockParams,
                                  @RequestParam(value = "page") String page );

    @RequestMapping(method = RequestMethod.POST, value = "/init/initStockUpAndDown")
    String initStockUpAndDown(@RequestParam(value = "pwd") String pwd);

    @RequestMapping(method = RequestMethod.POST, value = "/init/resetStockUpAndDown")
    String resetStockUpAndDown(@RequestParam(value = "pwd") String pwd);

    @RequestMapping(method = RequestMethod.POST, value = "/init/initSHList")
    String initSHList(@RequestParam(value = "pwd") String pwd);

    @RequestMapping(method = RequestMethod.POST, value = "/init/initSZList")
    String initSZList(@RequestParam(value = "pwd") String pwd);

    @RequestMapping(method = RequestMethod.POST, value = "/init/initStockToMongo")
    String initStockToMongo(@RequestParam(value = "pwd") String pwd);

    @RequestMapping(method = RequestMethod.POST, value = "/stock/fetchStockInfo")
    String fetchStockInfo();

    @RequestMapping(method = RequestMethod.POST, value = "/stock/fetchCompensation")
    String fetchCompensation();

}
