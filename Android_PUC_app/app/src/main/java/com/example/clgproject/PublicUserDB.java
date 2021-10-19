package com.example.clgproject;

public class PublicUserDB {

    public String fullName, phoneNo, email, pass;

    public PublicUserDB(){

    }

    public PublicUserDB(String fullName, String phoneNo, String email, String pass){
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
