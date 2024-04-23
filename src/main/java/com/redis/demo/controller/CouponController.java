package com.redis.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redis.demo.dto.CouponResponse;
import com.redis.demo.exception.NotFoundException;
import com.redis.demo.service.ICouponRedisService;
import com.redis.demo.service.ICouponService;
import com.redis.demo.service.impl.CouponRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/coupon")
public class CouponController {
    @Autowired
    ICouponService couponService;
    @Autowired
    ICouponRedisService couponRedisService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoupons() throws JsonProcessingException {
        String key = CouponRedisService.COUPON_PREFIX + CouponRedisService.COUPON_PREFIX_GET;
        List<CouponResponse> couponResponseList = couponRedisService.getAllCoupons(key);
        if (couponResponseList == null) {
            couponResponseList = couponService.getAllCoupons();
            couponRedisService.saveListCoupons(key, couponResponseList);
        }
        return ResponseEntity.ok(couponResponseList);
    }

    @PostMapping("/addNewCoupon")
    public ResponseEntity<?> save(@RequestBody CouponResponse couponResponse) {
        CouponResponse couponResponse1 = couponService.saveCoupon(couponResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(couponResponse1);
    }
    @GetMapping("/apply-coupon/{id}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long id){
        try{
            CouponResponse couponResponse = couponService.applyCoupon(id);
            return ResponseEntity.ok(couponResponse);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
