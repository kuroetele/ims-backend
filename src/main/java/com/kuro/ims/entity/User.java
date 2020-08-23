package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuro.ims.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends AuditableEntity
{

    @NotBlank(message = "name is required")
    private String name;

    @JsonIgnore
    private String password;

    private String address;

    @Size(min = 11, max = 11, message = "phone must be 11 digits")
    @NotBlank(message = "phone is required")
    private String phone;

    @Column(columnDefinition = "mediumtext")
    private String image;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email")
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @NotNull(message = "role is required")
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Transient
    private String plainPassword;

    @Column(columnDefinition = "boolean default false")
    private boolean passwordChangeRequired;

}
