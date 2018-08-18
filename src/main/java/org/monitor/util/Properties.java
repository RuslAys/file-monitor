package org.monitor.util;

public class Properties {
    private static String PATH_INPUT;
    private static String PATH_OUTPUT;

    public static String getPathInput() {
        if(PATH_INPUT == null){
            PropertyLoader propertyLoader = new PropertyLoader();
            PATH_INPUT = propertyLoader.getPathInput();
        }
        return PATH_INPUT;
    }

    public static String getPathOutput() {
        if(PATH_OUTPUT == null){
            PropertyLoader propertyLoader = new PropertyLoader();
            PATH_OUTPUT = propertyLoader.getPathOutput();
        }
        return PATH_OUTPUT;
    }
}
