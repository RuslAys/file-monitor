package org.monitor.filemonitor;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import javax.naming.ConfigurationException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileMonitor {
    private static final FileMonitor fileMonitor = new FileMonitor();
    private static long refreshDelay = 4000l;
    private URI monitoringDirectory = null;
    private static final String XML_EXTENSION = "xml";
    private static final int NUMBER_OF_THREADS = 10;

    public static FileMonitor getInstance(){
        return fileMonitor;
    }

    public void setRefreshDelay(long refreshDelay){
        this.refreshDelay = refreshDelay;
    }

    public void setMonitoringDirectory(URI monitoringDirectory) throws URISyntaxException,
            MalformedURLException{
        this.monitoringDirectory = monitoringDirectory;
    }

    private FileMonitor(){

    }

    public void start() throws ConfigurationException, MalformedURLException, Exception{
        if(monitoringDirectory == null){
            throw new IllegalStateException("Please set monitoring directory");
        }
        FileAlterationMonitor monitor = new FileAlterationMonitor(refreshDelay);

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        final File dir = new File(monitoringDirectory);
        Filter fileFilter = new Filter(XML_EXTENSION);
        FileAlterationObserver observer = new FileAlterationObserver(dir, fileFilter);
        FileAlterationListener listener =
                new FileAlterationListenerImpl(service);

        observer.addListener(listener);
        monitor.addObserver(observer);
        System.out.println("Started on " +  observer.getDirectory().getAbsolutePath() +
                "\nPress CTRL+C to stop");
        monitor.start();
    }
}