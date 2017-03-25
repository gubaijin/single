package com.gplucky.task.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ehsy_it on 2017/2/14.
 */
@FeignClient(value = "message")
public interface MessageFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/mail/sendSimpleMail")
    ResponseEntity<String> sendSimpleMail(@RequestParam(value = "sendTo") String sendTo,
                                          @RequestParam(value = "title") String title,
                                          @RequestParam(value = "content") String content);

}
