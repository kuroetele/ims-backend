package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuro.ims.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    private String name;

    @JsonIgnore
    private String password;

    private String address;

    private String phone;

    @Column(columnDefinition = "text")
    private String image;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Transient
    private String plainPassword;

    @Column(columnDefinition = "boolean default false")
    private boolean passwordChangeRequired;

}
