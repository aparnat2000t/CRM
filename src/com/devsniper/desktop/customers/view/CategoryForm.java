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
import com.devsniper.desktop.customers.component.JTextFieldExt;
import com.devsniper.desktop.customers.component.MessageBox;
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
 * Category form view
 *
 * @author Cem Ikta
 */
public class CategoryForm extends AbstractFormView<Category> {

    private Category category = null;
    private boolean isNewModel = false;

    // form fields
    private JTextFieldExt tfxName;
    private JTextPane tpNotes;

    /**
     * Gets new instance of category form
     *
     * @param controller category controller
     * @param category model
     */
    public CategoryForm(DataPageController<Category> controller, Category category) {
        super(AppController.get().getAppView(), controller);
        this.category = category;
        if (category.getId() == null) {
            isNewModel = true;
        }
    }

    @Override
    public void buildUI() {
        initComponents();
        tfxName = new JTextFieldExt(50);
        tfxName.requestFocus();
        tpNotes = new JTextPane();
        tpNotes.setPreferredSize(new Dimension(50, 100));
        tpNotes.setMargin(new Insets(0, 0, 0, 0));

        JPanel panel = new JPanel(new MigLayout("insets 20 10 10 10", "[][50:100,fill][fill,grow]", ""));

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Category.Form.Name")), "gap para");
        panel.add(tfxName, "span");

        panel.add(new JLabel(I18n.CUSTOMERS.getString("Category.Form.Notes")), "gap para");
        panel.add(new JScrollPane(tpNotes), "span");

        addPageToForm("", panel);

        popFields();
        pack();
        setSize(430, 330);
    }

    @Override
    public Category getEntity() {
        return category;
    }

    @Override
    public void popFields() {
        tfxName.setText(category.getName());
        tpNotes.setText(category.getNotes());
    }

    @Override
    public void pushFields() {
        category.setName(tfxName.getText());
        category.setNotes(tpNotes.getText());

        if (isNewModel) {
            // AppContoller.getAppCpntoller.getLoggedUser().getName();
            category.setCreatedBy("Admin User");
        } else {
            category.setUpdatedBy("Admin User");
        }
    }

    /**
     * Form validate
     *
     * @return true if no validate errors
     */
    @Override
    public boolean validateForm() {
        if (tfxName.getText().equals("")) {
            MessageBox.showWarning(I18n.CUSTOMERS.getString("Category.Form.NameError"));
            tfxName.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public String getFormIconPath() {
        return ViewHelpers.ICONS16 + "category.png";
    }

    @Override
    public String getFormTitle() {
        if (isNewModel) {
            return I18n.CUSTOMERS.getString("Category.Form.NewTitle");
        } else {
            return I18n.CUSTOMERS.getString("Category.Form.EditTitle");
        }
    }

    @Override
    public void onHelp() {
        MessageBox.showInfo("Help not implemented yet!");
    }

}
