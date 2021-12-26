/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.framework.AbstractDataPageView;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.framework.EntityTableColumn;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;

/**
 * Category Page View
 *
 * @author Cem Ikta
 */
public class CategoryPage extends AbstractDataPageView<Category> {

    @Override
    public void addTableColumns() {
        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Category.Page.Name"), "name", String.class, 300));

        getTableModel().addColumn(new EntityTableColumn(
                I18n.CUSTOMERS.getString("Category.Page.Notes"), "notes", String.class, 500));
    }

    @Override
    public String getIconPath() {
        return ViewHelpers.ICONS16 + "category.png";
    }

    @Override
    public String getTitle() {
        return I18n.CUSTOMERS.getString("Category.Page.Title");
    }

}
