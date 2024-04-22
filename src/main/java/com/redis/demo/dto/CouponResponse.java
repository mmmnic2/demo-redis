package com.redis.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponse {
    private Long id;
    private String couponCode;
    private int usageLimit;
    private int usageCount;

}
