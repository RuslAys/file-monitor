package org.monitor;


import org.monitor.filemonitor.FileMonitor;
import org.monitor.util.Properties;

import java.io.File;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
        String pathInput = Properties.getPathInput();
        String pathOutput = Properties.getPathOutput();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        File currentDirectory = new File("");
        File inputDirectory = new File(currentDirectory.getAbsolutePath()
                + File.separator + pathInput);
        File outputDirectory = new File(currentDirectory.getAbsolutePath()
                + File.separator + pathOutput);
        if(!inputDirectory.exists()){
            inputDirectory.mkdir();
        }
        if(!outputDirectory.exists()){
            outputDirectory.mkdir();
        }

        try {
            FileMonitor fileMonitor = FileMonitor.getInstance();
            fileMonitor.setRefreshDelay(1000L);
            fileMonitor.setMonitoringDirectory(
                    new File(pathInput).toURI());
            fileMonitor.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
