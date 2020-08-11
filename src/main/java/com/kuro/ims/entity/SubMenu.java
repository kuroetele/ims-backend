package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
}
