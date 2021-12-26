/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.framework.AbstractFormView;
import com.devsniper.desktop.customers.framework.DataPageController;
import com.devsniper.desktop.customers.model.Category;
import com.devsniper.desktop.customers.model.Customer;
import com.devsniper.desktop.customers.model.Country;
import com.devsniper.desktop.customers.component.JTextFieldExt;
import com.devsniper.desktop.customers.component.MessageBox;
import com.devsniper.desktop.customers.contoller.CustomerController;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;

/**
 * Customer form view
 *
 * @author Cem Ikta
 */
public class CustomerForm extends AbstractFormView<Customer> {

    private Customer customer = null;
    private boolean isNewModel = false;

    // General page
    private JTextFieldExt tfxCompanyName;
    private JComboBox cbCategory;
    private JTextFieldExt tfxTitle;
    private JTextFieldExt tfxFirstName;
    private JTextFieldExt tfxLastName;
    private JCheckBox chbActive;
    
    // Address page
    private JTextPane tpAddress;
    private JTextFieldExt tfxCity;
    private JTextFieldExt tfxRegion;
    private JTextFieldExt tfxPostalCode;
    private JComboBox cbCountry;
    
    // Communication page
    private JTextFieldExt tfxPhone;
    private JTextFieldExt tfxMobile;
    private JTextFieldExt tfxFax;
    private JTextFieldExt tfxEmail;
    private JTextFieldExt tfxHomepage;
    private JTextFieldExt tfxSkype;
    
    // Notes page
    private JTextPane tpNotes;

    /**
     * Gets new instance of customer form
     *
     * @param controller customer controller
     * @param customer model
     */
    public CustomerForm(DataPageController<Customer> controller, Customer customer) {
        super(AppController.get().getAppView(), controller);
        this.customer = customer;
        if (customer.getId() == null) {
            isNewModel = true;
        }
    }

    @Override
    public void buildUI() {
        initComponents();
        // add pages
        addPageToForm(I18n.CUSTOMERS.getString("Customer.Form.GeneralPage.Title"),
                buildGeneralPage());
        addPageToForm(I18n.CUSTOMERS.getString("Customer.Form.AddressPage.Title"),
                buildAddressPage());
        addPageToForm(I18n.CUSTOMERS.getString("Customer.Form.CommunicationPage.Title"),
                buildCommunicationPage());
        addPageToForm(I18n.CUSTOMERS.getString("Customer.Form.NotesPage.Title"),
                buildNotesPage());

        popFields();
        pack();
        setSize(550, 530);
    }

    /**
     * General page UI
     *
     * @return general page panel
     */
    private JPanel buildGeneralPage() {
        tfxCompanyName = new JTextFieldExt(100);
        cbCategory = new JComboBox(((CustomerController) controller).getCategoriesList().toArray());
        tfxTitle = new JTextFieldExt(50);
        tfxFirstName = new JTextFieldExt(50);
        tfxLastName = new JTextFieldExt(50);
        chbActive = new JCheckBox(I18n.CUSTOMERS.getString("Customer.Form.Active"));

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][50:100,fill][fill,grow]", ""));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.CompanyName")), "gap para");
        panel.add(tfxCompanyName, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Category")), "gap para");
        panel.add(cbCategory, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Title")), "gap para");
        panel.add(tfxTitle, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.FirstName")), "gap para");
        panel.add(tfxFirstName, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.LastName")), "gap para");
        panel.add(tfxLastName, "span");

        panel.add(ViewHelpers.createBoldTitledSeperator(
                I18n.CUSTOMERS.getString("Customer.Form.ActiveTitle")), "span,growx");

        panel.add(new JLabel(), "gap para");
        panel.add(chbActive, "span");

        return panel;
    }

    /**
     * Address page UI
     *
     * @return addres page panel
     */
    private JPanel buildAddressPage() {
        tpAddress = new JTextPane();
        tpAddress.setPreferredSize(new Dimension(20, 80));
        tpAddress.setMargin(new Insets(0, 0, 0, 0));
        tfxCity = new JTextFieldExt(50);
        tfxRegion = new JTextFieldExt(50);
        tfxPostalCode = new JTextFieldExt(50);
        cbCountry = new JComboBox(((CustomerController) controller).getCountriesList(true).toArray());

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][fill,grow]"));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Address")), "gap para");
        panel.add(new JScrollPane(tpAddress), "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.City")), "gap para");
        panel.add(tfxCity, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Region")), "gap para");
        panel.add(tfxRegion, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.PostalCode")), "gap para");
        panel.add(tfxPostalCode, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Country")), "gap para");
        panel.add(cbCountry, "span");

        return panel;
    }

    /**
     * Communication page UI
     *
     * @return communication page panel
     */
    private JPanel buildCommunicationPage() {
        tfxPhone = new JTextFieldExt(50);
        tfxMobile = new JTextFieldExt(50);
        tfxFax = new JTextFieldExt(50);
        tfxEmail = new JTextFieldExt(50);
        tfxHomepage = new JTextFieldExt(50);
        tfxSkype = new JTextFieldExt(50);

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][fill,grow]"));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Phone")), "gap para");
        panel.add(tfxPhone, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Mobile")), "gap para");
        panel.add(tfxMobile, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Fax")), "gap para");
        panel.add(tfxFax, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Email")), "gap para");
        panel.add(tfxEmail, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Homepage")), "gap para");
        panel.add(tfxHomepage, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Skype")), "gap para");
        panel.add(tfxSkype, "span");

        return panel;
    }

    /**
     * Notes page UI
     *
     * @return notes page panel
     */
    private JPanel buildNotesPage() {
        tpNotes = new JTextPane();
        tpNotes.setPreferredSize(new Dimension(50, 200));
        tpNotes.setMargin(new Insets(0, 0, 0, 0));

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][fill,grow]"));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Customer.Form.Notes")), "gap para");
        panel.add(new JScrollPane(tpNotes), "span");

        return panel;
    }

    @Override
    public Customer getEntity() {
        return customer;
    }

    @Override
    public void popFields() {
        // general page
        tfxCompanyName.setText(customer.getCompanyName());
        if (customer.getCategory() != null) {
            cbCategory.setSelectedItem(customer.getCategory());
        }
        tfxTitle.setText(customer.getContactTitle());
        tfxFirstName.setText(customer.getContactFirstName());
        tfxLastName.setText(customer.getContactLastName());
        chbActive.setSelected(customer.getActive());
        // address page
        tpAddress.setText(customer.getAddress());
        tfxCity.setText(customer.getCity());
        tfxRegion.setText(customer.getRegion());
        tfxPostalCode.setText(customer.getPostalCode());
        if (customer.getCountry() != null) {
            cbCountry.setSelectedItem(customer.getCountry());
        }
        // communication page
        tfxPhone.setText(customer.getPhone());
        tfxMobile.setText(customer.getMobile());
        tfxFax.setText(customer.getFax());
        tfxEmail.setText(customer.getEmail());
        tfxHomepage.setText(customer.getHomepage());
        tfxSkype.setText(customer.getSkype());
        // notes page
        tpNotes.setText(customer.getNotes());
    }

    @Override
    public void pushFields() {
        // general page
        customer.setCompanyName(tfxCompanyName.getText());
        customer.setCategory(Category.class.cast(cbCategory.getSelectedItem()));
        customer.setContactTitle(tfxTitle.getText());
        customer.setContactFirstName(tfxFirstName.getText());
        customer.setContactLastName(tfxLastName.getText());
        customer.setActive(chbActive.isSelected());
        // address page
        customer.setAddress(tpAddress.getText());
        customer.setCity(tfxCity.getText());
        customer.setRegion(tfxRegion.getText());
        customer.setPostalCode(tfxPostalCode.getText());
        if (cbCountry.getSelectedIndex() > 0) {
            customer.setCountry(Country.class.cast(cbCountry.getSelectedItem()));
        } else {
            customer.setCountry(null);
        }
        // communication page
        customer.setPhone(tfxPhone.getText());
        customer.setMobile(tfxMobile.getText());
        customer.setFax(tfxFax.getText());
        customer.setEmail(tfxEmail.getText());
        customer.setHomepage(tfxHomepage.getText());
        customer.setSkype(tfxSkype.getText());
        // notes page
        customer.setNotes(tpNotes.getText());

        if (isNewModel) {
            // AppContoller.getAppContoller.getLoggedUser().getName();
            customer.setCreatedBy("Admin User");
        } else {
            customer.setUpdatedBy("Admin User");
        }
    }

    /**
     * Form validate
     *
     * @return true if no validate errors
     */
    @Override
    public boolean validateForm() {
        if (tfxCompanyName.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Customer.Form.CompanyNameError"));
            tfxCompanyName.requestFocus();
            return false;
        }

        if (tfxFirstName.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Customer.Form.FirstNameError"));
            tfxFirstName.requestFocus();
            return false;
        }

        if (tfxLastName.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Customer.Form.LastNameError"));
            tfxLastName.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public String getFormIconPath() {
        return ViewHelpers.ICONS16 + "customer.png";
    }

    @Override
    public String getFormTitle() {
        if (isNewModel) {
            return I18n.CUSTOMERS.getString("Customer.Form.NewTitle");
        } else {
            return I18n.CUSTOMERS.getString("Customer.Form.EditTitle");
        }
    }
    
    @Override
    public boolean isMultiPageForm() {
        return true;
    }

    @Override
    public boolean isPrintable() {
        return true;
    }
    
    @Override
    public void onPrintPreview() {
        MessageBox.showInfo("Print preview not implemented yet!");
    }

    @Override
    public void onPrint() {
        MessageBox.showInfo("Print not implemented yet!");
    }

    @Override
    public void onHelp() {
        MessageBox.showInfo("Help not implemented yet!");
    }

}
