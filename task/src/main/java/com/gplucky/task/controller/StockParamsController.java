package com.gplucky.task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gplucky.common.bean.PageG;
import com.gplucky.common.controller.BaseController;
import com.gplucky.common.mybatis.model.StockParams;
import com.gplucky.task.service.StockParamsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("params")
public class StockParamsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(StockParamsController.class);

    @Autowired
    private StockParamsService stockParamsService;

    @ApiOperation(value = "查询股票指标信息", notes = "筛选查询对应股票指标信息(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "stockParams", value = "股票指标JSON", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "body", name = "page", value = "page", required = true, dataType = "String")
    })
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public ResponseEntity<String> select(@RequestParam(value = "stockParams", required = false) String stockParams,
                                         @RequestParam(value = "page", required = false) String page) {
        PageG pageG = PageG.convert(page);
        Page pageHelper = PageHelper.startPage(pageG.getPageNo(), pageG.getPageSize(), true);
        List<StockParams> list = stockParamsService.select(JSONObject.parseObject(stockParams, StockParams.class));
        return this.returnSuccessMsg(pageG.setCountAndTotalPage(pageG, pageHelper), JSON.toJSONString(list,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty));
    }

}
