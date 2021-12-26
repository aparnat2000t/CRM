/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.contoller;

import com.devsniper.desktop.customers.framework.AbstractDataPageController;
import com.devsniper.desktop.customers.framework.DataPageView;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.model.Customer;
import com.devsniper.desktop.customers.service.AbstractService;
import com.devsniper.desktop.customers.service.CategoryService;
import com.devsniper.desktop.customers.service.CountryService;
import com.devsniper.desktop.customers.service.CustomerService;
import static com.devsniper.desktop.customers.service.QueryParameter.with;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.view.CustomerForm;
import com.devsniper.desktop.customers.view.CustomerPage;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer Controller
 *
 * @author Cem Ikta
 */
public class CustomerController extends AbstractDataPageController<Customer> {

    @Override
    protected AbstractService<Customer> createService() {
        return new CustomerService();
    }

    @Override
    protected DataPageView<Customer> createDataPageView() {
        return new CustomerPage();
    }

    @Override
    public void openFormView(Customer customer) {
        new CustomerForm(this, customer).showDialog();
    }

    @Override
    public void onAddNew() {
        Customer customer = new Customer();
        customer.setActive(true);
        openFormView(customer);
    }

    /**
     * Gets categories from database.
     *
     * @return categories list
     */
    public List<Category> getCategoriesList() {
        return new CategoryService().getListWithNamedQuery(Category.FIND_ALL);
    }

    /**
     * Gets countries from database.
     *
     * @param addEmptyItem true if emtpy item exist
     * @return countries list
     */
    public List<Object> getCountriesList(boolean addEmptyItem) {
        List<Object> list = new ArrayList<>();

        if (addEmptyItem) {
            list.add(new Object() {
                @Override
                public String toString() {
                    return I18n.CUSTOMERS.getString("Customer.Form.SelectCountry");
                }
            });
        }

        list.addAll(new CountryService().getListWithNamedQuery(Country.FIND_ALL));

        return list;
    }

    @Override
    public List<Customer> getData(String filter, int start, int end) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery(), start, end);
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("companyName", "%" + filter + "%").
                    and("contactFirstName", "%" + filter + "%").
                    and("contactLastName", "%" + filter + "%").parameters(), start, end);
        }
    }

    @Override
    public int getDataSize(String filter) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery()).size();
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("companyName", "%" + filter + "%").
                    and("contactFirstName", "%" + filter + "%").
                    and("contactLastName", "%" + filter + "%").parameters()).size();
        }
    }

    @Override
    public String getNamedQuery() {
        return Customer.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Customer.FIND_BY_FILTER;
    }

    @Override
    public String getName() {
        return "CustomerController";
    }

}
