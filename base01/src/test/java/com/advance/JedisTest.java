package com.advance;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

public class JedisTest {
    JedisPool jedisPool = new JedisPool();

    @Test
    public void testJedis(){
        Jedis jedis = jedisPool.getResource();
        jedis.watch("key1");
        //开启事务
        Transaction transaction = jedis.multi();
        //命令入队
        transaction.set("key1", "hello");
        transaction.expire("key1", 20);
        //获取一个新的Jedis实例修改key1
        Jedis jedis2 = jedisPool.getResource();
        jedis2.set("key1", "world");
        //提交事务
        List<Object> result = transaction.exec();
        System.out.println(result);
        System.out.println(jedis.get("key1"));
    }

}
