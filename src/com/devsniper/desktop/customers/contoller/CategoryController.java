/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.contoller;

import com.devsniper.desktop.customers.framework.AbstractDataPageController;
import com.devsniper.desktop.customers.framework.DataPageView;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.service.AbstractService;
import com.devsniper.desktop.customers.service.CategoryService;
import static com.devsniper.desktop.customers.service.QueryParameter.with;
import com.devsniper.desktop.customers.view.CategoryPage;
import com.devsniper.desktop.customers.view.CategoryForm;
import java.util.List;

/**
 * Category Controller
 *
 * @author Cem Ikta
 */
public class CategoryController extends AbstractDataPageController<Category> {

    @Override
    protected AbstractService<Category> createService() {
        return new CategoryService();
    }

    @Override
    protected DataPageView<Category> createDataPageView() {
        return new CategoryPage();
    }

    @Override
    public void openFormView(Category category) {
        new CategoryForm(this, category).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new Category());
    }

    @Override
    public List<Category> getData(String filter, int start, int end) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery(), start, end);
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("name", "%" + filter + "%").parameters(), start, end);
        }
    }

    @Override
    public int getDataSize(String filter) {
        if (filter.equals("")) {
            return getService().getListWithNamedQuery(getNamedQuery()).size();
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("name", "%" + filter + "%").parameters()).size();
        }
    }

    @Override
    public String getNamedQuery() {
        return Category.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Category.FIND_BY_NAME;
    }

    @Override
    public String getName() {
        return "CategoryController";
    }

}
