package com.nixmash.jangles.core;

import com.google.inject.Inject;
import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by daveburke on 7/3/17.
 */
public class JanglesLocalizer {

    private final JanglesConfiguration janglesConfiguration;

    @Inject
    public JanglesLocalizer(JanglesConfiguration janglesConfiguration) {
        this.janglesConfiguration = janglesConfiguration;
    }

    public ResourceBundle get() {
        Locale currentLocale;
        currentLocale = LocaleUtils.toLocale(janglesConfiguration.currentLocale);
        return ResourceBundle.getBundle("messages", currentLocale);
    }

}
