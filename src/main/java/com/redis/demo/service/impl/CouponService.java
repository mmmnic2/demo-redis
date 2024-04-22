package com.redis.demo.service.impl;

import com.redis.demo.dto.CouponResponse;
import com.redis.demo.entity.Coupon;
import com.redis.demo.repository.CouponRepository;
import com.redis.demo.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService implements ICouponService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    CouponRepository couponRepository;
    @Override
    public List<CouponResponse> getAllCoupons() {
        List<Coupon> couponList = couponRepository.findAll();
        return couponList.stream()
                .map(Coupon::toCouponResponse)
                .toList();
    }


    @Override
    public CouponResponse findCouponById(Long id) {
        return null;
    }

    @Override
    public CouponResponse saveCoupon(CouponResponse couponResponse) {
        return null;
    }
}
