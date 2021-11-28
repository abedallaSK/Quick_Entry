package com.example.startapplication;

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
    private String companyName;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Account() {
    }


    public Account(String username,String name, String familyName,  String password, String email, String phoneNumber, String id, int type,String profileUri,String greenUri) {
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
