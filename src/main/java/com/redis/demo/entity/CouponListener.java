package com.redis.demo.entity;

import com.redis.demo.service.IBaseRedisService;
import com.redis.demo.service.ICouponRedisService;
import com.redis.demo.service.impl.CouponRedisService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class CouponListener {
    @Autowired
    ICouponRedisService couponRedisService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    IBaseRedisService baseRedisService;

    @PostPersist
    public void postPersist(Coupon coupon) {
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX + coupon.getCouponCode());
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX+CouponRedisService.COUPON_PREFIX_GET);
    }

    @PostUpdate
    public void postUpdate(Coupon coupon) {
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX + coupon.getCouponCode());
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX+CouponRedisService.COUPON_PREFIX_GET);
    }

    @PostRemove
    public void postRemove(Coupon coupon) {
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX + coupon.getCouponCode());
        baseRedisService.delete(CouponRedisService.COUPON_PREFIX+CouponRedisService.COUPON_PREFIX_GET);
    }
}
