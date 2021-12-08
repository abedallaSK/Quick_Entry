package com.example.startapplication.classes;
//Created by Abedalla
public class Account {
    private String name;
    private String familyName;
    private String username;
    private String Password;
    private String email;
    private String phoneNumber;
    private String greenUri;
    private int type;
    private   String profileUri;
    private int numberOfPeople;
    private  Boolean checkGreen;


    public Account() {
    }
    //start
    public Account(String username,String name, String familyName,  String password, String email, String phoneNumber, String id, int type,String profileUri,String greenUri,String key) {
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        Password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.greenUri = greenUri;
        this.type = type;
        this.profileUri=profileUri;
        this.id=id;
        this.key=key;
        this.checkGreen=false;
    }
    public Account(String username,String name, String familyName,  String password, String email, String phoneNumber, String id, int type,String profileUri,String key,int numberOfPeople) {
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        Password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfPeople=numberOfPeople;
        this.type = type;
        this.profileUri=profileUri;
        this.id=id;
        this.key=key;
    }
    public Account(String username,String name, String familyName,  String password, String email, String phoneNumber, String id, int type,String profileUri,String key) {
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        Password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.profileUri=profileUri;
        this.id=id;
        this.key=key;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGreenUri() {
        return greenUri;
    }

    public void setGreenUri(String greenUri) {
        this.greenUri = greenUri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
