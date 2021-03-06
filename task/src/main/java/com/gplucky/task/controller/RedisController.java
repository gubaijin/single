package com.gplucky.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.bean.UpAndDown;
import com.gplucky.common.constants.Constants;
import com.gplucky.common.controller.BaseController;
import com.gplucky.task.service.StockRedisService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ehsy_it on 2017/2/6.
 */
@Controller
@RefreshScope
@RequestMapping("redis")
public class RedisController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private StockRedisService stockRedisService;

    @Value("${redis_key_seq_max}")
    private String redis_key_seq_max;

    @ApiOperation(value = "得到连涨股票代码", notes = "根据传入的数量来获得指定连涨天数的股票代码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "upAndDown", value = "连涨连跌JSON对象", required = false, dataType = "String")
    })
    @RequestMapping(value = "getSeqUpByDays", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getSeqUpByDays(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(value = "upAndDown") String upAndDown) {
        UpAndDown upAndDownObj = JSONObject.parseObject(upAndDown, UpAndDown.class);
        Stream<Object> stream = stockRedisService.getSeqUpByDays(upAndDownObj.getNum(), upAndDownObj);
        List<Object> list = stream.collect(Collectors.toList());
        PageG page = new PageG();
        page.setPageSize(Constants.PAGE_SIZE_10);
        page.setCount(list.size());
        page.setPageNo(pageNo);
        /*return this.returnSuccessMsg(page, list.size() > 0 ?list.subList((pageNo-1) * Constants.PAGE_SIZE_10,
                pageNo * Constants.PAGE_SIZE_10 > list.size()? list.size():pageNo * Constants.PAGE_SIZE_10):null);*/
        return this.returnSuccessMsg(page, list.stream()
                .skip((pageNo - 1) * Constants.PAGE_SIZE_10).limit(Constants.PAGE_SIZE_10).collect(Collectors.toList()));
    }
}
