/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.framework;

/**
 * Abstract Page Controller
 *
 * @author Cem Ikta
 */
public abstract class AbstractPageController implements PageController {

    private PageView pageView;

    public AbstractPageController() {
    }

    /**
     * Create Page View for this controller.
     * 
     * @return page view
     */
    protected abstract PageView createPageView();

    @Override
    public PageView getPageView() {
        if (pageView == null) {
            pageView = createPageView();
            pageView.init(this);
        }
        
        return pageView;
    }

}
