package org.monitor.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User {
    private long timestamp;
    private String idUser;
    private String URL;
    private long time;
    private Date date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-y", Locale.ENGLISH);

    public int getDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDateFormat() {
        return dateFormat.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = Long.parseLong(timestamp);
        date = new Date(this.timestamp*1000L);
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = Long.parseLong(time);
    }

    @Override
    public String toString() {
        return getDateFormat()
                + "," + getIdUser()
                + "," + getURL() + "," + time;
    }
}
