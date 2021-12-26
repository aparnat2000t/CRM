/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.model;

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Category entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "category", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
})
@NamedQueries({
    @NamedQuery(name = Category.FIND_ALL,
            query = "SELECT c FROM Category c ORDER BY c.name"),
    @NamedQuery(name = Category.FIND_BY_NAME,
            query = "SELECT c FROM Category c WHERE c.name LIKE :name ORDER BY c.name")
})
@AttributeOverride(name = "id", column = @Column(name = "category_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Category extends BaseEntity {

    public static final String FIND_ALL = "Category.findAll";
    public static final String FIND_BY_NAME = "Category.findByName";

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "notes", length = 65535)
    private String notes;

    @OneToMany(mappedBy = "category")
    private List<Customer> customerList;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
