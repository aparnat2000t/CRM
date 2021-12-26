/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */

package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.contoller.DashboardController;
import com.devsniper.desktop.customers.framework.PageController;
import com.devsniper.desktop.customers.framework.PageView;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXHyperlink;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.decoration.DecorationAreaType;

/**
 * Dashboard Page View
 *
 * @author Cem Ikta
 */
public class DashboardPage implements PageView {

    private JPanel pageView;
    private PageController controller;

    public DashboardPage() {
    }

    @Override
    public void init(PageController controller) {
        this.controller = controller;
        initComponents();
    }

    /**
     * init components
     */
    private void initComponents() {
        pageView = new JPanel(new BorderLayout());
        pageView.add(getHeaderBar(), BorderLayout.NORTH);
        pageView.add(getCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * Header Bar of dashboard page view
     *
     * @return header bar
     */
    public JPanel getHeaderBar() {
        JPanel headerBar = new JPanel(new MigLayout("insets 2 2 2 2"));

        JLabel lblTitle = new JLabel(getTitle());
        lblTitle.setIcon(new ImageIcon(getClass().getResource(getIconPath())));
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 14));

        headerBar.setPreferredSize(new Dimension(lblTitle.getWidth(), lblTitle.getHeight() + 28));
        headerBar.add(lblTitle, "dock center, gapleft 4");

        SubstanceLookAndFeel.setDecorationType(headerBar, DecorationAreaType.HEADER);

        return headerBar;
    }
    
    /**
     * Center panel of dashboard page view
     * 
     * @return center panel 
     */
    private Component getCenterPanel() {
        JPanel centerPanel = new JPanel(new MigLayout("insets 200 200 200 200"));
        
        int customersCount = ((DashboardController)getController()).getCustomersCount();
        int categoriesCount = ((DashboardController)getController()).getCategoriesCount();
        int countriesCount = ((DashboardController)getController()).getCountriesCount();
        
        JPanel customerItem = createItemPanel(ViewHelpers.IMAGES + "customer.png",
            I18n.CUSTOMERS.getString("Dashboard.Home.LinkCustomers") + " (" + customersCount + ")", 
            new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppController.get().getAppView().onOpenCustomers();
                }
            }
        );

        JPanel categoryItem = createItemPanel(ViewHelpers.IMAGES + "category.png", 
            I18n.CUSTOMERS.getString("Dashboard.Home.LinkCategories") + " (" + categoriesCount + ")", 
            new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppController.get().getAppView().onOpenCategories();
                }
            }
        );

        JPanel countryItem = createItemPanel(ViewHelpers.IMAGES + "country.png", 
            I18n.CUSTOMERS.getString("Dashboard.Home.LinkCountries") + " (" + countriesCount + ")", 
            new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppController.get().getAppView().onOpenCountries();
                }
            }
        );
        
        centerPanel.add(customerItem, "gapright 70");
        centerPanel.add(categoryItem, "gapright 70");
        centerPanel.add(countryItem, "gapright 70");
        
        return centerPanel;
    }

    /**
     * Creates item panel.
     * 
     * @param title title 
     * @param iconPath icon
     * @param found found string
     * @param openAction action of hyperlink
     * @return  item panel
     */
    private JPanel createItemPanel(String iconPath, String title, final AbstractAction openAction) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        
        JLabel lblItem = new JLabel(new ImageIcon(getClass().getResource(iconPath)));
        lblItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblItem.addMouseListener(new MouseAdapter() { 
          @Override
          public void mousePressed(MouseEvent me) { 
            openAction.actionPerformed(null); 
          } 
        });                 
                
        JXHyperlink link = new JXHyperlink(openAction);
        link.setText(title);
        
        itemPanel.add(lblItem, BorderLayout.NORTH);
        itemPanel.add(link, BorderLayout.SOUTH);
        
        return itemPanel;
    }
    
    @Override
    public PageController getController() {
        return this.controller;
    }

    @Override
    public Component asComponent() {
        return this.pageView;
    }
    
    @Override
    public String getIconPath() {
        return ViewHelpers.ICONS16 + "dashboard.png";
    }

    @Override
    public String getTitle() {
        return I18n.CUSTOMERS.getString("Dashboard.Home.Dashboard");
    }
    
}
