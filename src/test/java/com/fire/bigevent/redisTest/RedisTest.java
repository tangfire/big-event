package com.fire.bigevent.redisTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 如果在测试类上添加@SpringBootTest注解，那么在单元测试执行前，会先初始化spring容器
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username","tangfire");
        operations.set("id","1",15, TimeUnit.SECONDS);

    }

    @Test
    public void test02(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String username =  operations.get("username");
        System.out.println(username);
    }

}
