package com.kuro.ims.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "category")
public class Category extends AuditableEntity
{
    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

}
