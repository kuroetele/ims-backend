package com.kuro.ims.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "category")
public class Category extends AuditableEntity
{
    private String name;

    private String description;

    @OneToMany
    private List<Product> products;

}
