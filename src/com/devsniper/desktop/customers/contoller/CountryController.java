/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.contoller;

import com.devsniper.desktop.customers.framework.AbstractDataPageController;
import com.devsniper.desktop.customers.framework.DataPageView;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.service.AbstractService;
import com.devsniper.desktop.customers.service.CountryService;
import static com.devsniper.desktop.customers.service.QueryParameter.with;
import com.devsniper.desktop.customers.view.CountryForm;
import com.devsniper.desktop.customers.view.CountryPage;
import java.util.List;

/**
 * Country Controller
 *
 * @author Cem Ikta
 */
public class CountryController extends AbstractDataPageController<Country> {

    @Override
    protected AbstractService<Country> createService() {
        return new CountryService();
    }

    @Override
    protected DataPageView<Country> createDataPageView() {
        return new CountryPage();
    }

    @Override
    public void openFormView(Country country) {
        new CountryForm(this, country).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new Country());
    }

    @Override
    public List<Country> getData(String filter, int start, int end) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery(), start, end);
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("code", "%" + filter + "%").
                    and("name", "%" + filter + "%").parameters(), start, end);
        }
    }

    @Override
    public int getDataSize(String filter) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery()).size();
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("code", "%" + filter + "%").
                    and("name", "%" + filter + "%").parameters()).size();
        }
    }

    @Override
    public String getNamedQuery() {
        return Country.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Country.FIND_BY_CODE_NAME;
    }

    @Override
    public String getName() {
        return "CountryController";
    }

}
