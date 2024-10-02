package com.petstoremanagement.Global;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static final Properties properties = new Properties();
    private static final String APP_PROPERTIES_FILE = "/application.properties";

    static {
        try {
            InputStream in = AppProperties.class.getResourceAsStream(APP_PROPERTIES_FILE);
            if(in != null){
                properties.load(in);
            }else {
                System.out.println("no properties file");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    public static void  setProperty(String key, String value){
        properties.setProperty(key,value);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(AppProperties.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE)));
//            FileOutputStream fileOutputStream = new FileOutputStream(APP_PROPERTIES_FILE);
            properties.store(fileOutputStream,null);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
