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
 * Country entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "country", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"code"}),
    @UniqueConstraint(columnNames = {"name"})
})
@NamedQueries({
    @NamedQuery(name = Country.FIND_ALL,
            query = "SELECT c FROM Country c ORDER BY c.code"),
    @NamedQuery(name = Country.FIND_BY_CODE_NAME,
            query = "SELECT c FROM Country c WHERE c.code LIKE :code OR c.name LIKE :name ORDER BY c.code")
})
@AttributeOverride(name = "id", column = @Column(name = "country_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Country extends BaseEntity {

    public static final String FIND_ALL = "Country.findAll";
    public static final String FIND_BY_CODE_NAME = "Country.findByCodeName";

    @Basic(optional = false)
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "notes", length = 65535)
    private String notes;

    @OneToMany(mappedBy = "country")
    private List<Customer> customerList;

    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
