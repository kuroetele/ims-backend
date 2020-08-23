package com.kuro.ims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "customer")
public class Customer extends AuditableEntity
{

    private String address;

    @Size(min = 11, max = 11, message = "phone must be 11 digits")
    @NotBlank(message = "phone is required")
    private String phone;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;

    @NotBlank(message = "name is required")
    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Column(columnDefinition = "mediumtext")
    private String  image;

}
