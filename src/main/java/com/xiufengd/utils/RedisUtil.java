package com.xiufengd.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 */
public final class RedisUtil {

    private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    private RedisUtil() {
    }

    private static RedisTemplate<Serializable, Serializable> redisTemplate = null;

    // 获取连接
    @SuppressWarnings("unchecked")
    private static RedisTemplate<Serializable, Serializable> getRedis() {
        if (redisTemplate == null) {
            synchronized (RedisUtil.class) {
                if (redisTemplate == null) {
                    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                    redisTemplate = (RedisTemplate<Serializable, Serializable>)wac.getBean("redisTemplate");
                }
            }
        }
        return redisTemplate;
    }

    public static final Serializable get(final String key) {
        //expire(key, 60000);
        return getRedis().opsForValue().get(key);
    }

    public static final void set(final String key, final Serializable value) {
        getRedis().boundValueOps(key).set(value);
        //expire(key, 60000);
    }
    
    public static final void set(final String key, final Serializable value,Integer expire) {
        getRedis().boundValueOps(key).set(value);
        expire(key, expire);
    }

    public static final Boolean exists(final String key) {
        //expire(key, 60000);
        return getRedis().hasKey(key);
    }

    public static final void del(final String key) {
        getRedis().delete(key);
    }

    public static final void delAll(final String pattern) {
        getRedis().delete(getRedis().keys(pattern));
    }

    public static final String type(final String key) {
        expire(key, 60000);
        return getRedis().type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     * 
     * @return
     */
    public static final Boolean expire(final String key, final int seconds) {
        return getRedis().expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 在某个时间点失效
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public static final Boolean expireAt(final String key, final long unixTime) {
        return getRedis().expireAt(key, new Date(unixTime));
    }

    public static final Long ttl(final String key) {
        return getRedis().getExpire(key, TimeUnit.SECONDS);
    }

    public static final void setrange(final String key, final long offset, final String value) {
        expire(key, 60000);
        getRedis().boundValueOps(key).set(value, offset);
    }

    public static final String getrange(final String key, final long startOffset, final long endOffset) {
        expire(key, 60000);
        return getRedis().boundValueOps(key).get(startOffset, endOffset);
    }

    public static final Serializable getSet(final String key, final String value) {
        expire(key, 60000);
        return getRedis().boundValueOps(key).getAndSet(value);
    }

    /** 递增 */
    public static Long incr(final String redisKey) {
        return getRedis().execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(redisKey.getBytes());
            }
        });
    }
    
    /** 生成主键策略 */
    public static String createId(String key) {
        String redisKey = "REDIS_TBL_" + key;
        String format = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return format + RedisUtil.incr(redisKey);
    }
    // 未完，待续...



    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public static Object getString(String key){
        return key==null?null:getRedis().opsForValue().get(key);
    }


    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean setString(String key, String value) {
        try {
            getRedis().opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 添加缓存
     *
     * @param key     键
     * @param value   值 是对象或者List集合
     * @param seconds 过期时间（秒）
     * @return true成功 false失败
     */
    public static boolean setObjectOrList(String key, Object value, long seconds) {
        try {
            //进行json转换
            String valueJsonStr = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue);
            getRedis().opsForValue().set(key, valueJsonStr, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("添加缓存失败", e);
            return false;
        }
    }

    /**
     * 批量删除缓存
     *
     * @param key 键：支持模糊查询。eg:keys*
     * @return true成功 false失败
     */
    public static boolean batchDelete(String key) {
        try {
            Set<Serializable> keys = getRedis().keys(key);
            getRedis().delete(keys);
            return true;
        } catch (Exception e) {
            log.error("删除缓存异常,key={}", key, e);
            return false;
        }
    }

    /**
     * 清空所有缓存数据
     */
    public static void flushAll(){
        getRedis().execute((RedisCallback) connection->{
            connection.flushAll();
            return null;
        });
    }
}
