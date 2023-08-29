package com.example.doan4.Models;

public class AppUser {


    int UserID;
    String UserName,UserAcc,UserPass,UserPhone,UserAddress;

    public AppUser(int userID, String userName, String userAcc, String userPass, String userPhone, String userAddress) {
        UserID = userID;
        UserName = userName;
        UserAcc = userAcc;
        UserPass = userPass;
        UserPhone = userPhone;
        UserAddress = userAddress;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAcc() {
        return UserAcc;
    }

    public void setUserAcc(String userAcc) {
        UserAcc = userAcc;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }
}
