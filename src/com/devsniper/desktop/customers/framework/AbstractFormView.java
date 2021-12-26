/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.framework;

import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.model.BaseEntity;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import org.jdesktop.swingx.JXStatusBar;

/**
 * Abstract Form View for data editing in JDialog.
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractFormView<T extends BaseEntity> extends JDialog {

    protected DataPageController<T> controller;
    protected JMenuBar menuBar;
    protected JToolBar toolBar;
    protected JTabbedPane tpPages;
    protected JXStatusBar xstatusBar;
    
    // menu and toolbar actions
    protected Action acSave;
    protected Action acPrintPreview;
    protected Action acPrint;
    protected Action acClose;
    protected Action acHelp;

    /**
     * Creates form view
     *
     * @param parent parent view
     * @param controller form's controller
     */
    public AbstractFormView(JFrame parent, DataPageController<T> controller) {
        super(parent);
        this.controller = controller;
    }

    /**
     * init components
     */
    public void initComponents() {
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon(getClass().getResource(getFormIconPath())).getImage());
        setTitle(getFormTitle());

        buildFormActions();
        setJMenuBar(buildMenuBar());
        getContentPane().add(buildToolBar(), BorderLayout.NORTH);
        getContentPane().add(buildStatusBar(), BorderLayout.SOUTH);

        if (isMultiPageForm()) {
            tpPages = new JTabbedPane();
            tpPages.setFocusable(false);
            getContentPane().add(tpPages, BorderLayout.CENTER);
        }
    }

    /**
     * Gets form view's title
     *
     * @return form view's title
     */
    public abstract String getFormTitle();

    /**
     * Gets form view's icon path
     *
     * @return form view's icon path
     */
    public abstract String getFormIconPath();

    /**
     * Builds form view components.
     * Call first initComponents() in this method.
     */
    public abstract void buildUI();

    /**
     * Pop model values to UI
     */
    public abstract void popFields();

    /**
     * Push UI values to model
     */
    public abstract void pushFields();

    /**
     * Save data of form view
     *
     * @return true if save is ok
     */
    public boolean onSave() {
        if (!validateForm()) {
            return false;
        }

        pushFields();
        controller.onSave(getEntity());

        return true;
    }

    /**
     * Validate fields before save
     *
     * @return
     */
    public boolean validateForm() {
        return true;
    }

    /**
     * Gets entity object
     *
     * @return entity
     */
    public abstract T getEntity();

    /**
     * Adds new page to form view.
     *
     * @param title page's title
     * @param page page panel
     */
    public void addPageToForm(String title, JPanel page) {
        if (isMultiPageForm()) {
            tpPages.add(title, page);
        } else {
            getContentPane().add(page, BorderLayout.CENTER);
        }
    }

    /**
     * Has this form multi pages?
     *
     * @return true if this form has multi pages.
     */
    public boolean isMultiPageForm() {
        return false;
    }

    /**
     * Is this form view printable?
     *
     * @return true if this form has print actions.
     */
    public boolean isPrintable() {
        return false;
    }

    /**
     * Print preview
     */
    public void onPrintPreview() {
    }

    /**
     * Print
     */
    public void onPrint() {
    }

    /**
     * Help
     */
    public abstract void onHelp();

    /**
     * Show dialog
     */
    public void showDialog() {
        buildUI();
        setLocationRelativeTo(AppController.get().getAppView());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    /**
     * Form view close
     */
    public void onCloseForm() {
        setVisible(false);
        dispose();
    }

    /**
     * Builds form view's menu bar
     *
     * @return menu bar
     */
    private JMenuBar buildMenuBar() {
        menuBar = new JMenuBar();

        // File menu
        JMenu mnuFile = new JMenu(I18n.COMMON.getString("AbstractFormView.Menu.File"));
        mnuFile.add(acSave);
        mnuFile.add(new JSeparator());
        if (isPrintable()) {
            mnuFile.add(acPrintPreview);
            mnuFile.add(acPrint);
            mnuFile.add(new JSeparator());
        }
        mnuFile.add(acClose);
        menuBar.add(mnuFile);

        // Help menu
        JMenu mnuHelp = new JMenu(I18n.COMMON.getString("AbstractFormView.Menu.Help"));
        mnuHelp.add(acHelp);
        menuBar.add(mnuHelp);

        return menuBar;
    }

    /**
     * Builds form view's toolbar
     *
     * @return toolBar action toolbar
     */
    private JToolBar buildToolBar() {
        toolBar = new JToolBar();
        toolBar.setRollover(true);

        toolBar.add(ViewHelpers.createToolButton(acSave, true, true));
        if (isPrintable()) {
            toolBar.add(ViewHelpers.createToolButton(acPrintPreview, true, true));
            toolBar.add(ViewHelpers.createToolButton(acPrint, true, true));
        }
        toolBar.add(ViewHelpers.createToolButton(acHelp, true, true));
        toolBar.add(ViewHelpers.createToolButton(acClose, true, true));

        return toolBar;
    }

    /**
     * Builds form view actions
     */
    private void buildFormActions() {
        acSave = new AbstractAction(I18n.COMMON.getString("Action.Save"),
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "save.png"))) {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (onSave()) {
                            controller.onRefresh();
                            onCloseForm();
                        }
                    }
                };
        acSave.putValue(Action.SHORT_DESCRIPTION, I18n.COMMON.getString("Action.Hint.Save"));
        acSave.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
        acSave.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        acPrintPreview = new AbstractAction(I18n.COMMON.getString("Action.PrintPreview"),
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "printpreview.png"))) {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        onPrintPreview();
                    }
                };
        acPrintPreview.putValue(Action.SHORT_DESCRIPTION, I18n.COMMON.getString("Action.Hint.PrintPreview"));
        acPrintPreview.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_V));
        acPrintPreview.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

        acPrint = new AbstractAction(I18n.COMMON.getString("Action.Print"),
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "print.png"))) {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        onPrint();
                    }
                };
        acPrint.putValue(Action.SHORT_DESCRIPTION, I18n.COMMON.getString("Action.Hint.Print"));
        acPrint.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
        acPrint.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));

        acHelp = new AbstractAction(I18n.COMMON.getString("Action.Help"),
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "help.png"))) {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        onHelp();
                    }
                };
        acHelp.putValue(Action.SHORT_DESCRIPTION, I18n.COMMON.getString("Action.Hint.Help"));
        acHelp.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
        acHelp.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        acClose = new AbstractAction(I18n.COMMON.getString("Action.Close"),
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "close.png"))) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.onRefresh();
                        onCloseForm();
                    }
                };
        acClose.putValue(Action.SHORT_DESCRIPTION, I18n.COMMON.getString("Action.Hint.Close"));
        acClose.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
        acClose.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
    }

    /**
     * Builds form view's status bar.
     *
     * @return xstatusBar
     */
    private JXStatusBar buildStatusBar() {
        xstatusBar = new JXStatusBar();
        xstatusBar.setPreferredSize(new Dimension(15, 20));

        return xstatusBar;
    }

}
