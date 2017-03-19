package com.gplucky.task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gplucky.common.controller.BaseController;
import com.gplucky.common.mybatis.model.StockHistory;
import com.gplucky.task.service.StockHistoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ehsy_it on 2017/1/28.
 */
@Controller
@RequestMapping("history")
public class StockHistoryController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(StockHistoryController.class);

    @Autowired
    private StockHistoryService stockHistoryService;

    @ApiOperation(value = "根据股票代码查询股票历史信息", notes = "升序排列")
    @ApiImplicitParam(paramType = "body", name = "code", value = "股票代码", required = true, dataType = "String")
    @RequestMapping(value = "selectByCode", method = RequestMethod.POST)
    public ResponseEntity<String> selectByCode(@RequestParam(value = "code", required = true) String code) {
        List<StockHistory> list = stockHistoryService.selectByCode(code);
        return this.returnSuccessMsg(JSON.toJSONString(list,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty));
    }

}
