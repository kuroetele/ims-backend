package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "orders")
public class Order extends AuditableEntity
{
    @ManyToOne
    private Customer customer;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @NotNull
    private BigDecimal netAmount;

    @NotNull
    private Double vatPercentage;

    @NotNull
    private BigDecimal grossAmount;

    private BigDecimal loyaltyDiscountAmount;

    @NotNull
    private BigDecimal totalPaid;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String currency;

    private String invoiceNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Formula("(select u.email from users u where created_by = u.id)")
    private String soldBy;

}
