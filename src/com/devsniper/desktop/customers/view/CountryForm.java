/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.framework.AbstractFormView;
import com.devsniper.desktop.customers.framework.DataPageController;
import com.devsniper.desktop.customers.component.JTextFieldExt;
import com.devsniper.desktop.customers.component.MessageBox;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;

/**
 * Country form view
 *
 * @author Cem Ikta
 */
public class CountryForm extends AbstractFormView<Country> {

    private Country country = null;
    private boolean isNewModel = false;

    // form fields
    private JTextFieldExt tfxCode;
    private JTextFieldExt tfxName;
    private JTextPane tpNotes;

    /**
     * Gets new instance of country form
     *
     * @param controller country controller
     * @param country model
     */
    public CountryForm(DataPageController<Country> controller, Country country) {
        super(AppController.get().getAppView(), controller);
        this.country = country;
        if (country.getId() == null) {
            isNewModel = true;
        }
    }

    @Override
    public void buildUI() {
        initComponents();
        tfxCode = new JTextFieldExt(10);
        tfxCode.requestFocus();
        tfxName = new JTextFieldExt(50);
        tpNotes = new JTextPane();
        tpNotes.setPreferredSize(new Dimension(50, 100));
        tpNotes.setMargin(new Insets(0, 0, 0, 0));

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][50:100,fill][fill,grow]", ""));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Country.Form.Code")), "gap para");
        panel.add(tfxCode, "wrap");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Country.Form.Name")), "gap para");
        panel.add(tfxName, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Country.Form.Notes")), "gap para");
        panel.add(new JScrollPane(tpNotes), "span");

        addPageToForm("", panel);

        popFields();
        pack();
        setSize(430, 330);
    }

    @Override
    public Country getEntity() {
        return country;
    }

    @Override
    public void popFields() {
        tfxCode.setText(country.getCode());
        tfxName.setText(country.getName());
        tpNotes.setText(country.getNotes());
    }

    @Override
    public void pushFields() {
        country.setCode(tfxCode.getText());
        country.setName(tfxName.getText());
        country.setNotes(tpNotes.getText());

        if (isNewModel) {
            // AppContoller.getAppContoller.getLoggedUser().getName();
            country.setCreatedBy("Admin User");
        } else {
            country.setUpdatedBy("Admin User");
        }
    }

    /**
     * Validate form fields
     *
     * @return true if no validate errors
     */
    @Override
    public boolean validateForm() {
        if (tfxCode.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Country.Form.CodeError"));
            tfxCode.requestFocus();
            return false;
        }
        if (tfxName.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Country.Form.NameError"));
            tfxName.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public String getFormIconPath() {
        return ViewHelpers.ICONS16 + "country.png";
    }

    @Override
    public String getFormTitle() {
        if (isNewModel) {
            return I18n.CUSTOMERS.getString("Country.Form.NewTitle");
        } else {
            return I18n.CUSTOMERS.getString("Country.Form.EditTitle");
        }
    }

    @Override
    public void onHelp() {
        MessageBox.showInfo("Help not implemented yet!");
    }

}
