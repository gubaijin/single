package com.gplucky.parameter;

import com.gplucky.common.bean.mongo.StockM;
import com.gplucky.parameter.feign.TaskFegin;
import com.gplucky.parameter.service.mongo.RSIRepository;
import com.gplucky.parameter.service.mongo.StockMRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableMongoRepositories
public class ParameterApplicationTests {

	@Autowired
	private RSIRepository rsiRepository;

	@Autowired
	private TaskFegin taskFegin;

	@Autowired
	private StockMRepository stockMRepository;

	@Test
	public void contextLoads() {
		/*Person person1 = new Person();
		person1.setAge(29);
		person1.setName("gbj");

		Person person2 = new Person();
		person2.setAge(28);
		person2.setName("ivy");

		Person ss = (Person) personRepository.insert(person1);
		Person dd = (Person) personRepository.insert(person2);*/
		/*RSI rsi = new RSI();
		rsi.setCode("888888");

		LinkedList<Map<String,Double>> RSIList = Lists.newLinkedList();
		Map<String, Double> map1 = Maps.newHashMap();
		map1.put("2017-03-01", 0.1);
		RSIList.add(0, map1);

		Map<String, Double> map2 = Maps.newHashMap();
		map2.put("2017-03-02", 0.2);
		RSIList.add(0, map2);

		Map<String, Double> map3 = Maps.newHashMap();
		map3.put("2017-03-03", 0.3);
		RSIList.add(0, map3);

		Map<String, Double> map4 = Maps.newHashMap();
		map4.put("2017-03-04", 0.4);
		RSIList.add(0, map4);

		Map<String, Double> map5 = Maps.newHashMap();
		map5.put("2017-03-05", 0.5);
		RSIList.add(0, map5);

		rsi.setRSIList(RSIList);
		rsiRepository.insert(rsi);*/

		/*ResponseEntity<String> ss = taskFegin.selectAllCode(null);
		HttpResult result = JSON.parseObject(ss.getBody(), HttpResult.class);*/
		StockM stockM = new StockM();
		stockM.setCode("000001");
		Example<StockM> var1 = Example.of(stockM);
		StockM stock = stockMRepository.findOne(var1);
		System.out.println(1);
	}

}
