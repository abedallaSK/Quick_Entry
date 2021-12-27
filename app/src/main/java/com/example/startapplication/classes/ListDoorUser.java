package com.example.startapplication.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListDoorUser {
    private   ArrayList <DoorUser> doorUsers=new ArrayList<>();
    public ListDoorUser(ArrayList<DoorUser> doorUsers) {
        this.doorUsers = doorUsers;
    }

    public ListDoorUser() {
    }

    /*public Integer getCounter() {
        if(counter==null) return 0;
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Boolean getIslock() {
        return islock;
    }

    public void setIslock(Boolean islock) {
        this.islock = islock;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }*/

    public  void  addUser(DoorUser doorUser)
    {
        doorUsers.add(0,doorUser);
        /*Collections.sort(doorUsers, new Comparator<DoorUser>() {
            public int compare(DoorUser o1, DoorUser o2) {
                return Integer.compare(o2.getRank(), o1.getRank());
            }
        });*/
    }

    public void removeUser(int  index)
    {
        doorUsers.remove(index);
       /* for(int i=0;i<=doorUsers.size();i++)
        {
            if(doorUsers.get(i).getTime().equals(name))
            {
                doorUsers.remove(doorUsers.get(i));
                break;
            }
        }*/
    }
    public ArrayList<DoorUser> getDoorUsers() {
        return doorUsers;
    }

    public void setDoorUsers(ArrayList<DoorUser> doorUsers) {
        this.doorUsers = doorUsers;
    }



}
