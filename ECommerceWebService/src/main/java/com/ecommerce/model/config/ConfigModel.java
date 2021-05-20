package com.ecommerce.model.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.math.NumberUtils;
import org.ini4j.Wini;

/**
 *
 * @author ngoclt2
 */
public class ConfigModel {

    private static Wini iniReader;
    public static ConfigModel INSTANCE = new ConfigModel();

    public ConfigModel() {
        try {
            iniReader = new Wini(new File("conf/conf.ini"));
        } catch (IOException ex) {
            Logger.getLogger(ConfigModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getString(String sectionName, String optionName, String defaultValue) {
        String str = iniReader.get(sectionName, optionName);
        if (str == null) {
            return defaultValue;
        }
        return str;
    }

    public int getInt(String sectionName, String optionName, int defaultValue) {
        String str = iniReader.get(sectionName, optionName);
        if (str == null) {
            return defaultValue;
        }
        return NumberUtils.toInt(str, defaultValue);
    }
}
