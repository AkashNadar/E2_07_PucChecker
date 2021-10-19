package com.example.clgproject;

public class PoliceUserDB {
    public String fullName, phoneNo, email, pId, pass;

    public PoliceUserDB() {
    }

    public PoliceUserDB(String fullName, String phoneNo, String email, String pId, String pass){

        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.pId = pId;
        this.pass = pass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
