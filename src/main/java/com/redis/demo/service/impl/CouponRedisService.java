package com.redis.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.demo.dto.CouponResponse;
import com.redis.demo.service.IBaseRedisService;
import com.redis.demo.service.ICouponRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class CouponRedisService implements ICouponRedisService {
    @Autowired
    IBaseRedisService baseRedisService;
    @Autowired
    ObjectMapper redisObjectMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    public static final String COUPON_PREFIX = "coupon";
    public static final String COUPON_PREFIX_GET = "get";


    @Override
    public List<CouponResponse> getAllCoupons(String key) throws JsonProcessingException {
        String json = (String) baseRedisService.get(key);
        return json != null ? redisObjectMapper.readValue(json, new TypeReference<List<CouponResponse>>() {
        }) : null;
    }

    @Override
    public void saveCoupon(String key, CouponResponse couponResponse) throws JsonProcessingException {
        String json = redisObjectMapper.writeValueAsString(couponResponse);
        baseRedisService.set(key, json);
    }

    @Override
    public void saveListCoupons(String key, List<CouponResponse> couponResponseList) throws JsonProcessingException {
        String json = redisObjectMapper.writeValueAsString(couponResponseList);
        baseRedisService.set(key, json);
    }
    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
    // clear cache every 2 minutes
    @Scheduled(fixedRate = 120000)
    public void clearCache(){
        clear();
    }
}
