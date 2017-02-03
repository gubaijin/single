package com.gplucky.common.utils;

import redis.clients.jedis.JedisCluster;

/**
 * Created by ehsy_it on 2017/1/30.
 */
//@Component
public class RedisUtils {

//    @Autowired
    private JedisCluster jedisCluster;

    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

/*    @Bean
    JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }*/

/*    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }*/
}
