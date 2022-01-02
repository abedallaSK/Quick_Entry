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
        this.doorUsers = new ArrayList<>();
    }


    public  void  addUser(DoorUser doorUser)
    {
        doorUsers.add(0,doorUser);

    }

    public void removeUser(int  index)
    {
        doorUsers.remove(index);
    }
    public ArrayList<DoorUser> getDoorUsers() {
        return doorUsers;
    }

    public void setDoorUsers(ArrayList<DoorUser> doorUsers) {
        this.doorUsers = doorUsers;
    }



}
