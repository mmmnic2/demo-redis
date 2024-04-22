package com.redis.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redis.demo.dto.CouponResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICouponRedisService {

    List<CouponResponse> getAllCoupons(String key) throws JsonProcessingException;
    void saveCoupon(String key, CouponResponse couponResponse) throws JsonProcessingException;
    void saveListCoupons(String key, List<CouponResponse> couponResponseList) throws JsonProcessingException;
}
