package org.monitor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final String PATH_TO_PROPERTIES = "config.properties";
    private Properties properties = new Properties();
    private InputStream inputStream =
            getClass().getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES);
    public String getPathInput(){
        try{
            properties.load(inputStream);
            return properties.getProperty("input_folder");
        }catch (IOException e){
            System.out.println("Something wrong with config.properties." +
                    "The names of the folders will be used by default");
            e.printStackTrace();
            return "input_files";
        }
    }

    public String getPathOutput(){
        try{
            properties.load(inputStream);
            return properties.getProperty("output_folder");
        }catch (IOException e){
            System.out.println("Something wrong with config.properties." +
                    "The names of the folders will be used by default");
            e.printStackTrace();
            return "output_files";
        }
    }

}
