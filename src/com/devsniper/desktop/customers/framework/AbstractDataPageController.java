/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.framework;

import com.devsniper.desktop.customers.model.BaseEntity;
import com.devsniper.desktop.customers.service.AbstractService;
import com.devsniper.desktop.customers.component.MessageBox;
import com.devsniper.desktop.customers.util.I18n;

/**
 * Abstract Data Page Controller.
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractDataPageController<T extends BaseEntity> 
        implements DataPageController<T> {

    private AbstractService<T> service;
    private DataPageView<T> dataPageView;

    public AbstractDataPageController() {
    }

    protected abstract AbstractService<T> createService();

    @Override
    public AbstractService<T> getService() {
        if (service == null) {
            service = createService();
        }
        return service;
    }

    protected abstract DataPageView<T> createDataPageView();

    @Override
    public DataPageView<T> getDataPageView() {
        if (dataPageView == null) {
            dataPageView = createDataPageView();
            dataPageView.init(this);
            dataPageView.refreshData();
        } else {
            dataPageView.refreshData();
        }
        
        return dataPageView;
    }

    @Override
    public void onEdit() {
        if (dataPageView == null) {
            return;
        }
        if (dataPageView.getSelectedModel() == null) {
            return;
        }
        openFormView(dataPageView.getSelectedModel());
    }

    @Override
    public void onDelete() {
        if (dataPageView == null) {
            return;
        }
        if (dataPageView.getSelectedModel() == null) {
            return;
        }
        if (MessageBox.showAskYesNo(I18n.COMMON.getString(
                "MessageBox.Confirm.Delete")) == MessageBox.YES_OPTION) {
            try {
                getService().remove(dataPageView.getSelectedModel());
                onRefresh();
            } catch (Exception e) {
                MessageBox.showError(I18n.COMMON.getString("Messages.Error.DeleteError"), e);
            }
        }
    }

    @Override
    public void onRefresh() {
        if (dataPageView != null) {
            dataPageView.refreshData();
        }
    }

    @Override
    public void onSave(T entity) {
        try {
            if (entity.getId() == null) {
                getService().create(entity);
            } else {
                getService().update(entity);
            }

        } catch (Exception e) {
            MessageBox.showError(I18n.COMMON.getString("Messages.Error.SaveError"), e);
        }
    }

}
