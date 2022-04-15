package com.kaituo.comparison.back.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/11/12 13:40
 * @Modified by:
 */

public class PropertyUtils {
    public static String getPropertiesValue(String key, String fileName) throws IOException {

        Properties p = new Properties();
//        File file = ResourceUtils.getFile("classpath:" + fileName);
//        File file = new ClassPathResource(fileName).getFile();

        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = classPathResource.getInputStream();
        try {
            p.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }

    public static String setPropertiesValue(String key, String value, String fileName) throws IOException {

        Properties p = new Properties();


        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(fileName);
        Resource resource = resources[0];
        File file = resource.getFile();
//获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
        InputStream stream = resource.getInputStream();
//        ClassPathResource classPathResource = new ClassPathResource(fileName);
//        InputStream inputStream = classPathResource.getInputStream();
//        File file = ResourceUtils.getFile("classpath:" + fileName);
//        File file = new ClassPathResource(fileName).getFile();
//        File file = new File("src\\main\\resources" + File.separator + (fileName == null ? "application." : fileName));
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

    public static String getPropertiesValue(String key) throws IOException {
        return getPropertiesValue(key, null);
    }

    public static void main(String[] args) throws IOException {

//        String recordTime = getPropertiesValue("recordTime", "recordTime.properties");
//        System.out.println(recordTime);

        String s = setPropertiesValue("recordTime", "666666", "recordTime.properties");
        System.out.println(s);


    }

}
