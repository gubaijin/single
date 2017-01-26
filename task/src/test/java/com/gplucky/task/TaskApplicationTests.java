package com.gplucky.task;

import com.gplucky.common.transport.data.RespData;
import com.gplucky.task.bean.StockResult;
import com.gplucky.task.service.impl.StockServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskApplicationTests {

	@Autowired
	private StockServiceImpl stockService;

	@Test
	public void getSHList() {
		RespData<StockResult> respData = stockService.getSHList();
		System.out.println(respData.getData().getTotalCount());
	}

}
