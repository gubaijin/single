package com.gplucky.task;

import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.task.service.impl.StockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskApplicationTests {

	@Autowired
	private StockServiceImpl stockService;

	@Test
	public void test(){
		List<Stock> list = stockService.getStockList();
		System.out.println(list.size());
	}

	@Test
	public void getSHList() {
		stockService.initSHList();
	}

	@Test
	public void insertStockNew(){
		Stock stock = new Stock();
		stock.setCode("090909");
		stockService.insertStockNew(stock);
	}

}
