/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers.view;

import com.devsniper.desktop.customers.contoller.DashboardController;
import com.devsniper.desktop.customers.component.AboutDialog;
import com.devsniper.desktop.customers.component.LookAndFeelDialog;
import com.devsniper.desktop.customers.component.MessageBox;
import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.contoller.CategoryController;
import com.devsniper.desktop.customers.contoller.CountryController;
import com.devsniper.desktop.customers.contoller.CustomerController;
import com.devsniper.desktop.customers.framework.View;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandButton.CommandButtonKind;
import org.jvnet.flamingo.common.JCommandToggleButton;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.ribbon.JRibbonBand;
import org.jvnet.flamingo.ribbon.JRibbonFrame;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenu;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryFooter;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryPrimary;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.jvnet.flamingo.ribbon.RibbonTask;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies;

/**
 * Application View
 *
 * @author Cem Ikta
 */
public class AppView extends JRibbonFrame {

    private JXStatusBar xstatusBar;
    private JPanel centerPanel;
    // show exit message box?
    private final boolean showExitDialog = false;

    /**
     * Creates a new instance of AppView
     *
     * @param title
     */
    public AppView(String title) {
        super(title);
        this.setIconImages(Arrays.asList(
                new ImageIcon(getClass().getResource(
                                ViewHelpers.ICONS16 + "app.png")).getImage(),
                new ImageIcon(getClass().getResource(
                                ViewHelpers.ICONS22 + "app.png")).getImage())
        );

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
        setSize(1024, 768);
        // for max screen
        // setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    /**
     * init ui components
     */
    private void initComponents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });

        getContentPane().add(buildStatusBar(), BorderLayout.SOUTH);

        // ribbon menu 
        configureRibbonMenu();

        // add dashboard page
        DashboardController dashboardController = new DashboardController();
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createMatteBorder(0, -1, 0, -1,
                ViewHelpers.getSubstanceComponentBorderColor(centerPanel)));
        centerPanel.add(dashboardController.getPageView().asComponent(), BorderLayout.CENTER);

        getContentPane().add(centerPanel, BorderLayout.CENTER);
        pack();
    }

    /**
     * Application exit
     * 
     * @see AppController#exit() 
     */
    private void exitForm(WindowEvent evt) {
        if (showExitDialog) {
            if (MessageBox.showAskYesNo(I18n.COMMON.getString(
                    "MessageBox.Confirm.ProgramExit")) == MessageBox.NO_OPTION) {
                return;
            }
        }

        AppController.get().exit();
    }

    /**
     * Open dashboard page
     */
    public void onOpenDashboard() {
        DashboardController controller = new DashboardController();
        addPageToCenter(controller.getPageView());
    }

    /**
     * Open customers page
     */
    public void onOpenCustomers() {
        CustomerController controller = new CustomerController();
        addPageToCenter(controller.getDataPageView());
    }

    /**
     * Open categories page
     */
    public void onOpenCategories() {
        CategoryController controller = new CategoryController();
        addPageToCenter(controller.getDataPageView());
    }

    /**
     * Open countries page
     */
    public void onOpenCountries() {
        CountryController controller = new CountryController();
        addPageToCenter(controller.getDataPageView());
    }

    /**
     * Help
     */
    public void onHelp() {
        MessageBox.showInfo("Help not implemented yet!");
    }

    /**
     * Show hide the status bar
     */
    public void onToogleStatusBar() {
        xstatusBar.setVisible(!xstatusBar.isVisible());
    }

    /**
     * Look and feel dialog
     */
    public void onLookAndFeel() {
        LookAndFeelDialog.showDialog();
    }

    /**
     * About Dialog
     */
    public void onAbout() {
        AboutDialog.showDialog();
    }

    /**
     * Settings Dialog
     */
    public void onSettings() {
        MessageBox.showInfo("Settings not implemented yet!");
    }

    /**
     * Adds page view or data page view to center
     *
     * @param page page view or data page view
     */
    public void addPageToCenter(View page) {
        centerPanel.removeAll();
        centerPanel.add(page.asComponent());
        centerPanel.revalidate();
        centerPanel.repaint();
        page.asComponent().requestFocus();
    }

    /**
     * Ribbon menu config
     */
    private void configureRibbonMenu() {
        // file task
        RibbonTask fileTask = new RibbonTask(
                I18n.COMMON.getString("AppView.File"),
                getActionsBand(),
                getAppExitBand());
        fileTask.setKeyTip("F");

        // extras task
        RibbonTask extrasTask = new RibbonTask(
                I18n.COMMON.getString("AppView.Extras"),
                getViewBand(),
                getExtrasBand());
        extrasTask.setKeyTip("E");

        RibbonTask helpTask = new RibbonTask(
                I18n.COMMON.getString("AppView.Help"),
                getHelpBand());
        helpTask.setKeyTip("H");

        this.getRibbon().addTask(fileTask);
        this.getRibbon().addTask(extrasTask);
        this.getRibbon().addTask(helpTask);

        configureTaskBar();
        configureApplicationMenu();

        // help button to left side
        this.getRibbon().configureHelp(ViewHelpers.createResizableIcon(
                new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "help.png"))),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onHelp();
                    }
                }
        );
    }

    /**
     * Ribbon menu top taskbar actions
     */
    protected void configureTaskBar() {
        JCommandButton cbtnDashboard = new JCommandButton("",
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS16 + "dashboard.png"))));
        cbtnDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenDashboard();
            }
        });

        JCommandButton cbtnCustomers = new JCommandButton("",
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS16 + "customer.png"))));
        cbtnCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCustomers();
            }
        });

        JCommandButton cbtnCategories = new JCommandButton("",
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS16 + "category.png"))));
        cbtnCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCategories();
            }
        });

        JCommandButton cbtnCountries = new JCommandButton("",
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS16 + "country.png"))));
        cbtnCountries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCountries();
            }
        });

        this.getRibbon().addTaskbarComponent(cbtnDashboard);
        this.getRibbon().addTaskbarComponent(cbtnCustomers);
        this.getRibbon().addTaskbarComponent(cbtnCategories);
        this.getRibbon().addTaskbarComponent(cbtnCountries);
    }

    /**
     * Ribbon Application Menu settings
     */
    protected void configureApplicationMenu() {
        RibbonApplicationMenuEntryPrimary amEntryDashboard = new RibbonApplicationMenuEntryPrimary(
                ViewHelpers.createResizableIcon(
                        new ImageIcon(getClass().getResource(ViewHelpers.ICONS22 + "dashboard.png"))),
                I18n.CUSTOMERS.getString("Action.Dashboard"),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onOpenDashboard();
                    }
                }, CommandButtonKind.ACTION_ONLY);
        amEntryDashboard.setActionKeyTip("D");

        RibbonApplicationMenuEntryPrimary amEntryCustomers = new RibbonApplicationMenuEntryPrimary(
                ViewHelpers.createResizableIcon(
                        new ImageIcon(getClass().getResource(ViewHelpers.ICONS22 + "customer.png"))),
                I18n.CUSTOMERS.getString("Action.Customers"),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onOpenCustomers();
                    }
                }, CommandButtonKind.ACTION_ONLY);
        amEntryCustomers.setActionKeyTip("C");

        RibbonApplicationMenuEntryPrimary amEntryCategories = new RibbonApplicationMenuEntryPrimary(
                ViewHelpers.createResizableIcon(
                        new ImageIcon(getClass().getResource(ViewHelpers.ICONS22 + "category.png"))),
                I18n.CUSTOMERS.getString("Action.Categories"),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onOpenCategories();
                    }
                }, CommandButtonKind.ACTION_ONLY);
        amEntryCategories.setActionKeyTip("A");

        RibbonApplicationMenuEntryPrimary amEntryCountries = new RibbonApplicationMenuEntryPrimary(
                ViewHelpers.createResizableIcon(
                        new ImageIcon(getClass().getResource(ViewHelpers.ICONS22 + "country.png"))),
                I18n.CUSTOMERS.getString("Action.Countries"),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onOpenCountries();
                    }
                }, CommandButtonKind.ACTION_ONLY);
        amEntryCountries.setActionKeyTip("O");

        RibbonApplicationMenuEntryFooter amEntryExit = new RibbonApplicationMenuEntryFooter(
                ViewHelpers.createResizableIcon(
                        new ImageIcon(getClass().getResource(ViewHelpers.ICONS16 + "exit.png"))),
                I18n.COMMON.getString("Action.Exit"),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        exitForm(null);
                    }
                });
        amEntryExit.setActionKeyTip("X");

        RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
        applicationMenu.addMenuEntry(amEntryDashboard);
        applicationMenu.addMenuEntry(amEntryCustomers);
        applicationMenu.addMenuEntry(amEntryCategories);
        applicationMenu.addMenuEntry(amEntryCountries);
        applicationMenu.addFooterEntry(amEntryExit);

        this.getRibbon().setApplicationMenu(applicationMenu);
    }

    /**
     * File menu actions band
     *
     * @return file menu actionsBand
     */
    private JRibbonBand getActionsBand() {
        JRibbonBand actionsBand = new JRibbonBand(
                I18n.COMMON.getString("AppView.ActionsBand"),
                new EmptyResizableIcon(22));
        actionsBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(actionsBand));

        JCommandButton cbtnDashboard = new JCommandButton(
                I18n.CUSTOMERS.getString("Action.Dashboard"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "dashboard.png"))));
        cbtnDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenDashboard();
            }
        });
        cbtnDashboard.setActionKeyTip("D");
        actionsBand.addCommandButton(cbtnDashboard, RibbonElementPriority.TOP);

        JCommandButton cbtnCustomers = new JCommandButton(
                I18n.CUSTOMERS.getString("Action.Customers"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "customer.png"))));
        cbtnCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCustomers();
            }
        });
        cbtnCustomers.setActionKeyTip("C");
        actionsBand.addCommandButton(cbtnCustomers, RibbonElementPriority.TOP);

        JCommandButton cbtnCategories = new JCommandButton(
                I18n.CUSTOMERS.getString("Action.Categories"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "category.png"))));
        cbtnCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCategories();
            }
        });
        cbtnCategories.setActionKeyTip("A");
        actionsBand.addCommandButton(cbtnCategories, RibbonElementPriority.TOP);

        JCommandButton cbtnCountries = new JCommandButton(
                I18n.CUSTOMERS.getString("Action.Countries"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "country.png"))));
        cbtnCountries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpenCountries();
            }
        });
        cbtnCountries.setActionKeyTip("O");
        actionsBand.addCommandButton(cbtnCountries, RibbonElementPriority.TOP);

        return actionsBand;
    }

    /**
     * File menu exit band
     *
     * @return exitBand
     */
    private JRibbonBand getAppExitBand() {
        JRibbonBand appExitBand = new JRibbonBand(
                I18n.COMMON.getString("AppView.AppExitBand"),
                new EmptyResizableIcon(22));
        appExitBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(appExitBand));

        JCommandButton cbtnAppExit = new JCommandButton(
                I18n.COMMON.getString("Action.Exit"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "exit.png"))));
        cbtnAppExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitForm(null);
            }
        });
        cbtnAppExit.setActionKeyTip("X");
        appExitBand.addCommandButton(cbtnAppExit, RibbonElementPriority.TOP);

        return appExitBand;
    }

    /**
     * Extras menu view band and command buttons
     *
     * @return viewBand
     */
    private JRibbonBand getViewBand() {
        JRibbonBand viewBand = new JRibbonBand(
                I18n.COMMON.getString("AppView.ViewBand"),
                new EmptyResizableIcon(22));

        JCommandToggleButton cbtnStatusBar = new JCommandToggleButton(
                I18n.COMMON.getString("Action.StatusBar"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "statusbar.png"))));
        cbtnStatusBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onToogleStatusBar();
            }
        });
        cbtnStatusBar.setActionKeyTip("S");
        cbtnStatusBar.getActionModel().setSelected(true);
        viewBand.addCommandButton(cbtnStatusBar, RibbonElementPriority.TOP);

        JCommandButton cbtnLookAndFeel = new JCommandButton(
                I18n.COMMON.getString("Action.LookAndFeel"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "laf.png"))));
        cbtnLookAndFeel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLookAndFeel();
            }
        });
        cbtnLookAndFeel.setActionKeyTip("T");
        viewBand.addCommandButton(cbtnLookAndFeel, RibbonElementPriority.TOP);

        return viewBand;
    }

    /**
     * Extras menu band and command buttons
     *
     * @return extrasBand
     */
    private JRibbonBand getExtrasBand() {
        JRibbonBand extrasBand = new JRibbonBand(
                I18n.COMMON.getString("AppView.ExtrasBand"),
                new EmptyResizableIcon(22));
        extrasBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(extrasBand));

        JCommandButton cbtnSettings = new JCommandButton(
                I18n.COMMON.getString("Action.Settings"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "settings.png"))));
        cbtnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSettings();
            }
        });
        cbtnSettings.setActionKeyTip("E");
        extrasBand.addCommandButton(cbtnSettings, RibbonElementPriority.TOP);

        return extrasBand;
    }

    /**
     * Help menu band and command buttons
     *
     * @return helpBand
     */
    private JRibbonBand getHelpBand() {
        JRibbonBand helpBand = new JRibbonBand(
                I18n.COMMON.getString("AppView.HelpBand"),
                new EmptyResizableIcon(22));
        helpBand.setResizePolicies(
                CoreRibbonResizePolicies.getCorePoliciesRestrictive(helpBand));

        JCommandButton cbtnHelp = new JCommandButton(
                I18n.COMMON.getString("AppView.Help"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "help.png"))));
        cbtnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onHelp();
            }
        });
        cbtnHelp.setActionKeyTip("H");
        helpBand.addCommandButton(cbtnHelp, RibbonElementPriority.TOP);

        JCommandButton cbtnAbout = new JCommandButton(
                I18n.COMMON.getString("AppView.Help.About"),
                ViewHelpers.createResizableIcon(new ImageIcon(getClass().getResource(
                                        ViewHelpers.ICONS22 + "about.png"))));
        cbtnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAbout();
            }
        });
        cbtnAbout.setActionKeyTip("A");
        helpBand.addCommandButton(cbtnAbout, RibbonElementPriority.TOP);

        return helpBand;
    }

    /**
     * Create XStatusBar with user name and date
     */
    private JXStatusBar buildStatusBar() {
        xstatusBar = new JXStatusBar();
        xstatusBar.setPreferredSize(new Dimension(15, 20));

        JLabel lblUser = new JLabel();
        lblUser.setText(I18n.COMMON.getString("AppView.StatusBar.User") + " : Admin User ");
        xstatusBar.add(lblUser, new JXStatusBar.Constraint(400));

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("EEEE', 'dd. MMMM yyyy");
        JLabel lblDate = new JLabel(" " + sdf.format(new Date()).toString());
        xstatusBar.add(lblDate, new JXStatusBar.Constraint(300));

        return xstatusBar;
    }
}
