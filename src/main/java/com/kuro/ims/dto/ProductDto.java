package com.kuro.ims.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDto
{
    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "category is required")
    private Long categoryId;

    @Positive(message = "available quantity must be greater than 0")
    private Long availableQuantity;

    @NotNull(message = "cost price is required")
    @Positive(message = "cost price must be greater than 0")
    private BigDecimal costPrice;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "sku is required")
    private String serialNumber;

    @NotNull(message = "min quantity is required")
    @Positive(message = "min quantity must be greater than 0")
    private Long minQuantity;

    @NotNull(message = "max quantity is required")
    @Positive(message = "max quantity must be greater than 0")
    private Long maxQuantity;

    private String image;
}
