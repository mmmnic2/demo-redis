package com.redis.demo.service;

import com.redis.demo.dto.CouponResponse;

import java.util.List;

public interface ICouponService {
    List<CouponResponse> getAllCoupons();
    CouponResponse findCouponById(Long id);
    CouponResponse saveCoupon(CouponResponse couponResponse);
}
