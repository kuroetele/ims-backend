package com.kuro.ims.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "menu")
public class Menu extends AuditableEntity
{
    private String icon;

    private String name;

    private String route;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private List<SubMenu> subMenus;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

}
