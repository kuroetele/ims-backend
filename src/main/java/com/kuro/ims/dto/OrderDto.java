package com.kuro.ims.dto;

import com.kuro.ims.type.PaymentType;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto
{
    private Long customerId;

    @Size(min = 1, message = "you need to select at least 1 product")
    private Map<Long, Integer> productAndQuantity;

    @NotNull(message = "payment type is required")
    private PaymentType paymentType;

    private Double discountPercentage = 0D;

    private boolean redeemLoyaltyPoints;
}
