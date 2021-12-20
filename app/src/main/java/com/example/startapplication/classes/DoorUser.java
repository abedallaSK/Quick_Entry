package com.example.startapplication.classes;

public class DoorUser {
    String name;
    String key;
    String time;
    int rank;

    public DoorUser(String name, String key, String time,int rank) {
        this.name = name;
        this.key = key;
        this.time = time;
        this.rank=rank;
    }
    public DoorUser() {
    }
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
