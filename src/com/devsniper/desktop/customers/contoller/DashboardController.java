/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.contoller;

import com.devsniper.desktop.customers.framework.AbstractPageController;
import com.devsniper.desktop.customers.framework.PageView;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.model.Customer;
import com.devsniper.desktop.customers.service.CategoryService;
import com.devsniper.desktop.customers.service.CountryService;
import com.devsniper.desktop.customers.service.CustomerService;
import com.devsniper.desktop.customers.view.DashboardPage;

/**
 * Dashboard Controller.
 *
 * @author Cem Ikta
 */
public class DashboardController extends AbstractPageController {

    @Override
    protected PageView createPageView() {
        return new DashboardPage();
    }

    /**
     * Gets customers count from database.
     *
     * @return customers count
     */
    public int getCustomersCount() {
        return new CustomerService().getListWithNamedQuery(Customer.FIND_ALL).size();
    }

    /**
     * Gets categories count from database.
     *
     * @return categories count
     */
    public int getCategoriesCount() {
        return new CategoryService().getListWithNamedQuery(Category.FIND_ALL).size();
    }

    /**
     * Gets countries count from database.
     *
     * @return countries count
     */
    public int getCountriesCount() {
        return new CountryService().getListWithNamedQuery(Country.FIND_ALL).size();
    }

    @Override
    public String getName() {
        return "DashboardController";
    }

}
