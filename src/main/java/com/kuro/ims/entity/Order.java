package com.kuro.ims.entity;

import com.kuro.ims.type.PaymentType;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "orders")
public class Order extends AuditableEntity
{
    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    private BigDecimal netAmount;

    private Double vatPercentage;

    private BigDecimal grossAmount;

    private Double discountPercentage;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String currency;

    private String invoiceNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

}
