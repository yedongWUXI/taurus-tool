package com.kaituo.comparison.back.common.util;

import com.kaituo.comparison.back.core.config.ftp.FtpConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/11/12 13:40
 * @Modified by:
 */

public class PropertyUtilsOld {
    public static String getPropertiesValue(String key, String fileName) {
        Properties p = new Properties();
        File file = new File("D:\\yuying\\zyd" + File.separator + (fileName == null ? "application" : fileName));
        try {
            InputStream is = new FileInputStream(file);
            p.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }

    public static String setPropertiesValue(String key, String value, String fileName) {

        Properties p = new Properties();
        File file = new File("D:\\yuying\\zyd" + File.separator + (fileName == null ? "application" : fileName));
        try {
            InputStream is = new FileInputStream(file);

            p.load(is);
            p.setProperty(key, value);
            FileWriter fw = new FileWriter(file);
            p.store(fw, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }

    public static String getPropertiesValue(String key) {
        return getPropertiesValue(key, null);
    }


}
