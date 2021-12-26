/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.framework.EntityTableColumn;
import com.devsniper.desktop.customers.framework.AbstractDataPageView;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;

/**
 * Country Page View
 *
 * @author Cem Ikta
 */
public class CountryPage extends AbstractDataPageView<Country> {

    @Override
    public void addTableColumns() {
        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Country.Page.Code"), "code", String.class, 100));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Country.Page.Name"), "name", String.class, 200));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Country.Page.Notes"), "notes", String.class, 200));
    }

    @Override
    public String getIconPath() {
        return ViewHelpers.ICONS16 + "country.png";
    }

    @Override
    public String getTitle() {
        return I18n.CUSTOMERS.getString("Country.Page.Title");
    }

}
