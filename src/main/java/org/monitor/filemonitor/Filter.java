package org.monitor.filemonitor;

import java.io.File;
import java.io.FileFilter;

public class Filter implements FileFilter {
    String ext;

    Filter(String ext){
        this.ext = ext;
    }

    @Override
    public boolean accept(File file) {
        if(!file.isFile())
            return false;
        String name = file.getName();
        if(name.endsWith(ext)
                && name.charAt(
                        name.length() - ext.length() - 1) == '.') {
            return true;
        }
        return false;
    }
}
