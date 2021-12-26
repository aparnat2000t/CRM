/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
 */
package com.devsniper.desktop.customers;

import com.devsniper.desktop.customers.component.Splash;
import com.devsniper.desktop.customers.contoller.AppController;
import com.devsniper.desktop.customers.util.I18n;
import com.devsniper.desktop.customers.util.ViewHelpers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CustomersDesktop extends AppController {

    private final static Logger LOGGER = Logger.getLogger(CustomersDesktop.class.getName());

    public static Splash splash;

    public CustomersDesktop() {
    }

    public static void main(String[] args) {
    	
        // set locale for i18n
        Locale.setDefault(new Locale("en", "US"));
        // for german
        //Locale.setDefault(new Locale("de", "DE"));
        // for turkish
        //Locale.setDefault(new Locale("tr", "TR"));
        
        // application splash form
        splash = new Splash(I18n.CUSTOMERS.getString("App.Title"),
                ViewHelpers.ICONS16 + "app.png",
                ViewHelpers.IMAGES + "splash.png");
        splash.setVisible(true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller = new CustomersDesktop();
                controller.setSplash(splash);
                controller.start();
            }
        });
    }
    }
  