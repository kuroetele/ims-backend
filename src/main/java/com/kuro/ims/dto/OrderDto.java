package com.kuro.ims.dto;

import com.kuro.ims.type.PaymentType;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto
{
    private Long customerId;

    private Map<Long, Integer> productAndQuantity;

    private PaymentType paymentType;

    private Double discountPercentage = 0D;
}
