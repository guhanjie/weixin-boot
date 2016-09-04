/** 
 * Project Name:		weixin-boot 
 * Package Name:	com.guhanjie.util 
 * File Name:			TTLCache.java 
 * Create Date:		2016年9月4日 下午3:03:27 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class Name:      TTLCache<br/>
 * Description:     [带时效的缓存实现，可用于Session会话缓存等应用场景]
 *                   <p>该缓存有两种模式：<br/>
 *                          一种模式是按进入时间失效逐出，即缓存项进入时间超时则逐出（此模式是为了支持缓存信息的及时更新）；此为默认模式<br/>
 *                          另一种模式是按最近访问时间失效逐出，即缓存项最近访问时间超时则逐出</p>
 * @time                2016年8月19日 上午9:44:28
 * @author          GUHANJIE
 * @version         1.0.0 
 * @since           JDK 1.7 
 */
public class TTLCache<K, V> {
    
    private volatile long TTL;  //以秒为单位
    private final static int DEFAULT_CACHE_SIZE = 16;
    private final static float DEFAULT_LOAD_FACTOR = 0.75f;
    private final static int ExpiredByInTime = 0;
    private final static int ExpiredByLastAccess = 1;
    private int expiredPolicy = 0;
    private LinkedHashMap<K, TimedValue<V>> map;
        
    public TTLCache(int cacheSize, int ttlSeconds, int expiredMod) {
        expiredPolicy = expiredMod;
        TTL = ttlSeconds * 1000L;
        //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容
        int capacity = (int) Math.ceil(cacheSize / DEFAULT_LOAD_FACTOR) + 1;
        map = new LinkedHashMap <K, TimedValue<V>> (capacity, DEFAULT_LOAD_FACTOR, true) {
            private static final long serialVersionUID = 1L;
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, TimedValue<V>> eldest) {
                //缓存中最老项是否过期失效，若失效则删除
                return System.currentTimeMillis() - eldest.getValue().timestamp > TTL;  
            }
        };
    }

    public TTLCache(int ttlSeconds, int expiredMode) {
        this(DEFAULT_CACHE_SIZE, ttlSeconds, expiredMode);
    }
    
    public TTLCache(int ttlSeconds) {
        this(DEFAULT_CACHE_SIZE, ttlSeconds, ExpiredByInTime);
    }
    
    public void put(K key, V value) {
        map.put(key, new TimedValue(value));
    }

    public V get(K key) {
         TimedValue<V> v = map.get(key);
         if(v == null) {
             return null;
         }
         if(System.currentTimeMillis() - v.timestamp > TTL) {   //若该缓存项最近访问时间过期，则将其删除
             v.value = null;
             remove(key);
         }
         else {
             if(expiredPolicy == ExpiredByLastAccess) {
                 v.timestamp = System.currentTimeMillis();  //更新该缓存项的最近访问时间（模式2：按最近访问时间失效）
             }
         }
         return v.value;
    }

    public void remove(K key) {
        map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }
    
    /**
     * Method Name: forceRemoveExpires<br/>
     * Description:         [强制删除缓存中已失效的项，此方法一般不建议使用，除非需要减小缓存大小]
     * @author              GUHANJIE
     * @time                    2016年8月19日 上午11:36:12
     */
    public void forceRemoveExpires() {
        //LinkedHashMap按访问顺序有序，删除排列在前面的已失效的缓存项
        Iterator<Entry<K, TimedValue<V>>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<K, TimedValue<V>> entry = iterator.next();
            if(System.currentTimeMillis() - entry.getValue().timestamp > TTL) {
                iterator.remove();
            }
            else {
                break;
            }
        }
    }

    /**
     * Method Name: setTTL<br/>
     * Description:         [可以更新缓存失效时间]
     * @author              GUHANJIE
     * @time                    2016年8月19日 下午2:34:05
     * @param ttlSeconds
     */
    public void setTTL(int ttlSeconds) {
        this.TTL = ttlSeconds * 1000L;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, TimedValue<V>> entry : map.entrySet()) {
            sb.append(String.format("%s:%s-%tT ", entry.getKey(), entry.getValue().value, entry.getValue().timestamp));
        }
        return sb.toString();
    }
    
    class TimedValue<V> {
        V value;
        long timestamp;
        
        TimedValue(V value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        final TTLCache c = new TTLCache(5);
        Thread t = new Thread() {
            public void run() {
                System.out.println(String.format("%tT", System.currentTimeMillis()));
                for(int i=1; i<6; i++) {
                    c.put("key"+i, "value"+i);
                }
            }
        };
        t.start();
        for(int i=0; i< 10; i++) {
            Thread.sleep(1000);
            c.get("key"+i);
            if(i==8) {
                c.put("key8", "value8");
            }
            if(i==9) {
                c.forceRemoveExpires();
            }
            System.out.print(String.format("%tT", System.currentTimeMillis()) +"======= ");
            System.out.println(c.toString());
        }
    }
    
}
