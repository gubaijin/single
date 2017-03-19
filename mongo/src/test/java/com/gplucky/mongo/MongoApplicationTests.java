package com.gplucky.mongo;

import com.gplucky.mongo.model.Person;
import com.gplucky.mongo.service.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableMongoRepositories
public class MongoApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void contextLoads() {
		Person person1 = new Person();
		person1.setAge(29);
		person1.setName("gbj");

		Person person2 = new Person();
		person2.setAge(28);
		person2.setName("ivy");

		Person ss = (Person) personRepository.insert(person1);
		Person dd = (Person) personRepository.insert(person2);
		System.out.println(1);
	}

}
