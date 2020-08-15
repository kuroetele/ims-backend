package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuro.ims.type.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sub_menu")
public class SubMenu extends AuditableEntity
{
    private int priority;

    private String name;

    private String route;

    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
}
