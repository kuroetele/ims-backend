package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuro.ims.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String address;

    private String phone;

    private String image;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private Role role;

}
