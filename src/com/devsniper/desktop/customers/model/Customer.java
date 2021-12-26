/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Customer entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = Customer.FIND_ALL,
            query = "SELECT c FROM Customer c ORDER BY c.companyName"),
    @NamedQuery(name = Customer.FIND_BY_FILTER,
            query = "SELECT c FROM Customer c WHERE c.companyName LIKE :companyName OR "
            + "c.contactFirstName LIKE :contactFirstName OR "
            + "c.contactLastName LIKE :contactLastName ORDER BY c.companyName")
})
@AttributeOverride(name = "id", column = @Column(name = "customer_id",
        nullable = false, columnDefinition = "BIGINT UNSIGNED"))
public class Customer extends BaseEntity {

    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_BY_FILTER = "Customer.findByFilter";

    @Basic(optional = false)
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "contact_title", length = 50)
    private String contactTitle;

    @Column(name = "contact_first_name", length = 50)
    private String contactFirstName;

    @Column(name = "contact_last_name", length = 50)
    private String contactLastName;

    @Lob
    @Column(name = "address", length = 65535)
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "fax", length = 50)
    private String fax;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "homepage", length = 50)
    private String homepage;

    @Column(name = "skype", length = 50)
    private String skype;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private boolean active;

    @Lob
    @Column(name = "notes", length = 65535)
    private String notes;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country country;

    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

    public Customer() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
