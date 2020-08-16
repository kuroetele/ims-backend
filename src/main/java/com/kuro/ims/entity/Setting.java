package com.kuro.ims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity
@Data
@Table(name="setting")
public class Setting extends AuditableEntity
{
    private String address;

    private String companyName;

    private String currency;

    private String email;

    private String phone;

    @Column(columnDefinition = "text")
    private String image;

    private Double vatPercentage;
}
