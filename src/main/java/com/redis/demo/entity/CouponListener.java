package com.redis.demo.entity;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;

public class CouponListener {
    @PostPersist
    public void postPersist(Coupon coupon){

    }
    @PostUpdate
    public void postUpdate(Coupon coupon){

    }
    @PostRemove
    public void postRemove(Coupon coupon){

    }
}
