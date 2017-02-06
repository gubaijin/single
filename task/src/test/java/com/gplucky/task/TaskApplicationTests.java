package com.gplucky.task;

import com.gplucky.common.mybatis.dao.StockMapper;
import com.gplucky.common.mybatis.model.Stock;
import com.gplucky.common.mybatis.model.StockExample;
import com.gplucky.task.service.impl.StockHistoryServiceImpl;
import com.gplucky.task.service.impl.StockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskApplicationTests {

	@Autowired
	private StockServiceImpl stockService;
	@Autowired
	private StockHistoryServiceImpl stockHistoryServiceImpl;
	@Autowired
	private StockMapper stockMapper;

	@Test
	public void test() throws ParseException {
		/*List<Stock> list = stockService.getStockList();
		System.out.println(list.size());*/

		/*List<StockHistory> list = stockHistoryServiceImpl.selectStockHistoryByDate(LocalDate.of(2017, 2, 3));
		System.out.println(list.size());*/

		/*System.out.println(DateUtils.getDateStartFormat(LocalDate.now()));
		System.out.println(DateUtils.getDateEndFormat(LocalDate.now()));*/

		Stock stock = new Stock();
		stock.setUpdateTime(new Date());
		stock.setCode("900946");
		StockExample example = new StockExample();
		example.createCriteria().andCodeEqualTo(stock.getCode());
		int updateFlg = stockMapper.updateByExampleSelective(stock, example);
		System.out.println(updateFlg);
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
