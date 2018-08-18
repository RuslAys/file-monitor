package org.monitor.core;

import org.monitor.model.AvTimeUser;
import org.monitor.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsersSeparationByDate {

    public List<User> listOfUsersSeparatedByDay(List<User> userList){
        List<User> separated = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < userList.size();i++) {
            long timestamp = userList.get(i).getTimestamp();
            long secs = userList.get(i).getTime();
            Date dateStart = new Date(timestamp * 1000L);
            Date dateEnd = new Date(timestamp*1000L + secs*1000L);
            if (getDay(dateStart) < getDay(dateEnd) ) {
                String tmp = getYear(dateStart) + "-"
                        + (getMonth(dateStart) + 1) + "-" + (getDay(dateStart) + 1);
                Date midnight;
                long timeUntilMidnight = 0;
                long timeAfterMidnight = 0;
                try {
                    midnight = dateFormat.parse(tmp);
                    timeUntilMidnight = (midnight.getTime() - dateStart.getTime())/1000L;
                    timeAfterMidnight = (dateEnd.getTime() - midnight.getTime())/1000L;
                }catch (ParseException e){
                    e.printStackTrace();
                    System.out.println("Incorrect format to parse");
                }
                User userUntilMidnight = createUser(userList, i, timeUntilMidnight, dateStart);
                separated.add(userUntilMidnight);

                User userAfterMidnight = createUser(userList, i, timeAfterMidnight, dateEnd);
                separated.add(userAfterMidnight);
            }else {
                separated.add(userList.get(i));
            }
        }
        return separated;
    }

    private int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    private int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    private User createUser(List<User> userList, int index, long time, Date date){
        User user = new User();
        user.setIdUser(userList.get(index).getIdUser());
        user.setTime(String.valueOf(time));
        user.setTimestamp(String.valueOf(date.getTime() / 1000L));
        user.setURL(userList.get(index).getURL());
        return user;
    }

    public List<List<AvTimeUser>> separateForListsByDate(List<AvTimeUser> userList){
        List<List<AvTimeUser>> list = new ArrayList<>();

        int indexOfList = 0;
        int day = userList.get(0).getDay();

        while(indexOfList != userList.size()){
            List<AvTimeUser> tmpList = new ArrayList<>();
            for(int i = indexOfList; i < userList.size(); i++){
                if(day != userList.get(i).getDay()){
                    indexOfList = i;
                    day = userList.get(i).getDay();
                    break;
                }else{
                    tmpList.add(userList.get(i));
                    indexOfList++;
                }
            }

            Collections.sort(tmpList, (AvTimeUser o1, AvTimeUser o2)->{
                int diff = o1.getUserID().length() - o2.getUserID().length();
                if(diff == 0){
                    return o1.getUserID().compareTo(o2.getUserID());
                }else{
                    return diff;
                }
            });
            list.add(tmpList);
        }
        return list;
    }
}
