package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AuditableEntity
{
    private String name;

    private String description;

    @ManyToOne
    private Category category;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long categoryId;

    private Long availableQuantity;

    private BigDecimal costPrice;

    private BigDecimal price;

    @Column(unique = true)
    private String serialNumber;

    private boolean enabled;

    private Long minQuantity;

    private Long maxQuantity;

}
