/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.framework.AbstractDataPageView;
import com.devsniper.desktop.customers.framework.AbstractPreviewPanel;
import com.devsniper.desktop.customers.framework.EntityTableColumn;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.model.Customer;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;

/**
 * Customer Page View
 *
 * @author Cem Ikta
 */
public class CustomerPage extends AbstractDataPageView<Customer> {

    @Override
    public void addTableColumns() {
        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.CompanyName"),
                "companyName", String.class, 300));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.FirstName"),
                "contactFirstName", String.class, 200));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.LastName"),
                "contactLastName", String.class, 200));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Category"),
                "category", Category.class, 200));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Phone"),
                "phone", String.class, 150));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Mobile"),
                "mobile", String.class, 150, false, false));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Fax"),
                "fax", String.class, 150, false, false));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Email"),
                "email", String.class, 150));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Homepage"),
                "homepage", String.class, 150, false, false));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Customer.Page.Active"),
                "active", Boolean.class, 50, false, false));
    }

    @Override
    public String getIconPath() {
        return ViewHelpers.ICONS16 + "customer.png";
    }

    @Override
    public String getTitle() {
        return I18n.CUSTOMERS.getString("Customer.Page.Title");
    }

    @Override
    public AbstractPreviewPanel getPreviewPanel() {
        return new CustomerPreview();
    }

}
