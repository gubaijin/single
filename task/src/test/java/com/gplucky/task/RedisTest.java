package com.gplucky.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ehsy_it on 2017/1/30.
 */
@RunWith(SpringRunner.class)
@ImportResource({"classpath:/common.xml"})
@SpringBootTest
@Configuration
@ComponentScan("com.gplucky.common")
public class RedisTest {

    @Test
    public void set() {
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("172.17.0.3", 6379));
        set.add(new HostAndPort("172.17.0.4", 6379));
        set.add(new HostAndPort("172.17.0.5", 6379));
        set.add(new HostAndPort("172.17.0.6", 6379));
        set.add(new HostAndPort("172.17.0.7", 6379));
        set.add(new HostAndPort("172.17.0.8", 6379));
        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("redisCluster4Test", "2016-3-22");
        String value = jedisCluster.get("redisCluster4Test");
        System.out.println(value);
    }
}
