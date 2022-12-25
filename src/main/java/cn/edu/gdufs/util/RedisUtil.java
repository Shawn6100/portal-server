package cn.edu.gdufs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description: Redis工具类
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DefaultRedisScript<Integer> redisScript;

    // 判断key是否存在
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /* 字符串操作 */

    // 获取缓存值
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    // 添加缓存数据
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 添加缓存数据和缓存过期时间
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    // 删除缓存数据
    public void del(String... key) {
        if (key == null || key.length == 0) {
            return;
        }
        if (key.length == 1) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete(Arrays.asList(key));
        }
    }

    // 自增
    public void incr(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void decr(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 获取剩余时间
     *
     * @param key key
     * @return -1为未设置；-2为键不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param time 过期时间，单位为秒
     */
    public void setExpire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /* 列表操作 */

    // 左端添加
    public void lPush(String key, Object... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    // 左端移除
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // 移除指定元素
    public void lRem(String key, Object value) {
        redisTemplate.opsForList().remove(key, 0, value);
    }

    /* 脚本操作 */

    // 执行活动报名的lua脚本
    public int executeSignupScript(String numKey, String userListKey, long userId) {
        return Objects.requireNonNull(redisTemplate.execute(redisScript, Arrays.asList(numKey, userListKey), userId));
    }

}
