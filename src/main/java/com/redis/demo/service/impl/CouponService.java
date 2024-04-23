package com.redis.demo.service.impl;

import com.redis.demo.dto.CouponResponse;
import com.redis.demo.entity.Coupon;
import com.redis.demo.exception.NotFoundException;
import com.redis.demo.repository.CouponRepository;
import com.redis.demo.service.IBaseRedisService;
import com.redis.demo.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService implements ICouponService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    IBaseRedisService baseRedisService;

    @Override
    public List<CouponResponse> getAllCoupons() {
        List<Coupon> couponList = couponRepository.findAll();
        return couponList.stream()
                .map(Coupon::toCouponResponse)
                .toList();
    }


    @Override
    public CouponResponse findCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new NotFoundException("not found" + id));
        return coupon.toCouponResponse();
    }

    @Override
    public CouponResponse saveCoupon(CouponResponse couponResponse) {
        Coupon coupon = couponRepository.save(toCoupon(couponResponse));
        return coupon.toCouponResponse();
    }


    @Override
    public CouponResponse applyCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("not found" + couponId));
        int count = coupon.getUsageCount();
        int limit = coupon.getUsageLimit();
        String key = CouponRedisService.COUPON_PREFIX + couponId;
        Boolean temp = redisTemplate.hasKey(key);
        if(Boolean.FALSE.equals(temp)){
            baseRedisService.set(key, count);
        }
        int countUser = Math.toIntExact(baseRedisService.increase(key));
        if(countUser > limit){
            throw new NotFoundException("Coupon has reached its usage limit.");
        }
        System.out.println("so luong coupon da dung: " + countUser );
        coupon.setUsageCount(countUser);
        return couponRepository.save(coupon).toCouponResponse();
    }



    private Coupon toCoupon(CouponResponse couponResponse){
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponResponse.getCouponCode());
        coupon.setUsageCount(couponResponse.getUsageCount());
        coupon.setUsageLimit(couponResponse.getUsageLimit());
        return coupon;
    }
}
