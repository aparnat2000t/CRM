/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.component.IconLabel;
import com.devsniper.desktop.customers.framework.AbstractPreviewPanel;
import com.devsniper.desktop.customers.model.Customer;
import com.devsniper.desktop.customers.util.ViewHelpers;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Customer preview panel
 *
 * @author Cem Ikta
 */
public class CustomerPreview extends AbstractPreviewPanel<Customer> {

    private Customer customer = null;

    private JLabel lblCompanyName;
    private JLabel lblPerson;
    private IconLabel lblLocation;
    private IconLabel lblPhone;
    private IconLabel lblMobile;
    private IconLabel lblEmail;
    private IconLabel lblHomepage;

    public CustomerPreview() {
        super();
    }

    @Override
    public void buildUI() {
        lblCompanyName = new JLabel();
        lblPerson = new JLabel();
        lblLocation = new IconLabel(new ImageIcon(getClass().getResource(
                ViewHelpers.ICONS16 + "location.png")));

        lblPhone = new IconLabel(new ImageIcon(getClass().getResource(
                ViewHelpers.ICONS16 + "phone.png")));
        lblMobile = new IconLabel(new ImageIcon(getClass().getResource(
                ViewHelpers.ICONS16 + "mobile.png")));

        lblEmail = new IconLabel(new ImageIcon(getClass().getResource(
                ViewHelpers.ICONS16 + "email.png")));
        lblHomepage = new IconLabel(new ImageIcon(getClass().getResource(
                ViewHelpers.ICONS16 + "homepage.png")));

        JPanel panel = new JPanel(new MigLayout("insets 10 20 10 20"));

        panel.add(lblCompanyName, "wrap");
        panel.add(lblPerson, "wrap");
        panel.add(lblLocation, "wrap");
        panel.add(lblPhone, "wrap");
        panel.add(lblMobile, "wrap");
        panel.add(lblEmail, "wrap");
        panel.add(lblHomepage, "wrap");

        addCenterComponent(panel);
    }

    @Override
    public void popFields() {
        customer = (Customer) getEntity();

        lblCompanyName.setText(customer.getCompanyName());
        lblPerson.setText(customer.getContactTitle() + " "
                + customer.getContactFirstName() + " "
                + customer.getContactLastName());

        String location = customer.getCity();
        if (customer.getCountry() != null) {
            location = location + ", " + customer.getCountry().getName();
        }

        lblLocation.setText(location);
        lblPhone.setText(customer.getPhone());
        lblMobile.setText(customer.getMobile());
        lblEmail.setText(customer.getEmail());
        lblHomepage.setText(customer.getHomepage());
    }

}
