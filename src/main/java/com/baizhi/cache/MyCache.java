/*package com.baizhi.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MyCache implements Cache {

    private String id;

    public MyCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
    //将查询结果放入缓存中
    @Override
    public void putObject(Object key, Object value) {
        //将查询出的结果放入redis当中  stringRedisTemplate
        RedisTemplate redisTemplate = (RedisTemplate) MyApplication.getBeanByName("redisTemplate");

        *//*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Article.class);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);*//*

        redisTemplate.opsForHash().put(id,key,value);
    }
    // 根据key获取缓存中的数据
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) MyApplication.getBeanByName("redisTemplate");
        return redisTemplate.opsForHash().get(id,key);
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) MyApplication.getBeanByName("redisTemplate");
        redisTemplate.delete(id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}*/
