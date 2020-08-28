package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AuditableEntity
{
    @NotBlank(message = "product name is required")
    private String name;

    private String description;

    @NotNull(message = "category is required")
    @ManyToOne
    private Category category;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    @Positive(message = "available quantity must be greater than 0")
    private Long availableQuantity;

    @Positive(message = "cost price must be greater than 0")
    private BigDecimal costPrice;

    @Positive(message = "price must be greater than 0")
    private BigDecimal price;

    @Column(unique = true)
    private String serialNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Min(value = 0, message = "min quantity cannot be less than 0")
    private Long minQuantity;

    @Positive(message = "max quantity must be greater than 0")
    private Long maxQuantity;

    @Column(columnDefinition = "mediumtext")
    private String image;

}
