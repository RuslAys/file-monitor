package org.monitor.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AvTimeUser {
    private String userID;
    private String URL;
    private long avTime;
    private long timestamp;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-y", Locale.ENGLISH);

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        date = new Date(this.timestamp*1000L);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setAvTime(long avTime) {
        this.avTime = avTime;
    }

    public String getUserID() {
        return userID;
    }

    public String getURL() {
        return URL;
    }

    public long getAvTime() {
        return avTime;
    }

    public String getDateFormat() {
        return dateFormat.format(date);
    }

    public int getDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public String toString() {
        return getDateFormat() + ","
                + getUserID() + "," + getURL() + "," + getAvTime();
    }
}
