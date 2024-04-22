package com.redis.demo.entity;

import com.redis.demo.dto.CouponResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table

@EntityListeners(CouponListener.class)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String couponCode;
    @Column
    private int usageLimit;
    @Column
    private int usageCount;
    public CouponResponse toCouponResponse(){
        return CouponResponse.builder()
                .id(this.id)
                .couponCode(this.couponCode)
                .usageLimit(this.usageLimit)
                .usageCount(this.usageCount)
                .build();
    }
}
