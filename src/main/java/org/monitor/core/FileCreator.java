package org.monitor.core;

import org.monitor.util.Properties;
import org.monitor.util.PropertyLoader;
import org.monitor.model.AvTimeUser;
import org.monitor.model.User;
import org.monitor.xml_parser.XmlReader;
import org.monitor.xml_parser.XmlWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileCreator {
    private static final String PREFIX = "avg_";
    public void createFile(File file){
        AvCalculator avCalculator = new AvCalculator();
        UsersSeparationByDate usbd = new UsersSeparationByDate();
        XmlReader xmlReader = new XmlReader();
        XmlWriter xmlWriter = new XmlWriter();
        try {
            List<User> users = usbd.listOfUsersSeparatedByDay(
                    xmlReader.parse(file.getAbsolutePath())
            );

            List<AvTimeUser> usersWithAvTime = avCalculator.calculateAverageTime(users);
            List<List<AvTimeUser>> listsSortedByDate = usbd.separateForListsByDate(usersWithAvTime);

            xmlWriter.write(listsSortedByDate, createPath(file));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String createPath(File file) throws IOException{
        File currentDirectory = new File("");
        StringBuilder builder = new StringBuilder(currentDirectory.getCanonicalPath());
        builder.append(File.separator)
                .append(Properties.getPathOutput())
                .append(File.separator)
                .append(PREFIX )
                .append(file.getName());
        return builder.toString();
    }
}
