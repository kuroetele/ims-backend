package com.kuro.ims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "customer")
public class Customer extends AuditableEntity
{

    private String address;

    private String phone;

    private String email;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

}
