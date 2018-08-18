package org.monitor.core;

import org.monitor.model.AvTimeUser;
import org.monitor.model.User;

import java.util.ArrayList;
import java.util.List;

public class AvCalculator {
    public List<AvTimeUser> calculateAverageTime(List<User> userList){
        List<AvTimeUser> list = new ArrayList<>();
        List<List<User>> listsOfUsersWithSameUrls =
                getListsOfUsersWithSameUrls(userList);
        for(int i = 0; i < listsOfUsersWithSameUrls.size(); i++){
            AvTimeUser avTimeUser = new AvTimeUser();
            avTimeUser.setURL(listsOfUsersWithSameUrls.get(i).get(0).getURL());
            avTimeUser.setUserID(listsOfUsersWithSameUrls.get(i).get(0).getIdUser());
            avTimeUser.setTimestamp(listsOfUsersWithSameUrls.get(i).get(0).getTimestamp());
            if(listsOfUsersWithSameUrls.get(i).size() == 1){
                avTimeUser.setAvTime(listsOfUsersWithSameUrls.get(i).get(0).getTime());
            }else{
                long sum = 0;
                for(int j = 0; j < listsOfUsersWithSameUrls.get(i).size(); j++){
                    sum = sum + listsOfUsersWithSameUrls.get(i).get(j).getTime();
                }
                avTimeUser.setAvTime(sum / (long) listsOfUsersWithSameUrls.get(i).size());
            }
            list.add(avTimeUser);
        }
        return list;
    }

    private List<List<User>> getListsOfUsersWithSameUrls(List<User> userList){
        List<List<User>> listsOfUsersWithSameUrls = new ArrayList<>();

        int indexOfLists = 0;

        List<User> usersWithSameUrls = new ArrayList<>();
        usersWithSameUrls.add(userList.get(0));
        listsOfUsersWithSameUrls.add(usersWithSameUrls);
        userList.remove(indexOfLists);

        while (userList.size() != 0){
            for(int i = 0; i < userList.size(); i++){
                User user = listsOfUsersWithSameUrls.get(indexOfLists).get(0);
                if(user.getIdUser().equals(userList.get(i).getIdUser())){
                    if(user.getURL().equals(userList.get(i).getURL())){
                        if(user.getDay() == userList.get(i).getDay()){
                            listsOfUsersWithSameUrls.get(indexOfLists).add(userList.get(i));
                            userList.remove(i);
                        }
                    }
                }
            }
            if(userList.size() != 0){
                List<User> tmpUserWithSameUrls = new ArrayList<>();
                tmpUserWithSameUrls.add(userList.get(0));
                listsOfUsersWithSameUrls.add(tmpUserWithSameUrls);
                userList.remove(0);
                indexOfLists++;
            }
        }

        return listsOfUsersWithSameUrls;
    }
}
