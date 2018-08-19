package org.monitor.filemonitor;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.monitor.core.FileCreator;

import java.io.File;
import java.util.concurrent.ExecutorService;

public class FileAlterationListenerImpl implements FileAlterationListener {

    private ExecutorService service;

    public FileAlterationListenerImpl(ExecutorService service) {
        this.service = service;
    }

    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("Monitoring...");
    }

    @Override
    public void onDirectoryCreate(File file) {

    }

    @Override
    public void onDirectoryChange(File file) {

    }

    @Override
    public void onDirectoryDelete(File file) {

    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("Read " + file.getName());
        service.execute(()->{
            FileCreator fileCreator = new FileCreator();
            fileCreator.createFile(file);
        });
    }

    @Override
    public void onFileChange(File file) {

    }

    @Override
    public void onFileDelete(File file) {

    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
