package com.kuro.ims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity
@Data
@Table(name="setting")
public class Setting extends AuditableEntity
{
    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "company name is required")
    private String companyName;

    @NotBlank(message = "currency is required")
    private String currency;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;

    @Size(min = 11, max = 11, message = "phone must be 11 digits")
    @NotBlank(message = "phone is required")
    private String phone;

    @Column(columnDefinition = "mediumtext")
    private String image;

    private Double vatPercentage;
}
