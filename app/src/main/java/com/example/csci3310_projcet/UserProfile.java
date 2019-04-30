package com.example.csci3310_projcet;

public class UserProfile {
    public String Email;
    public String Name;
    public String Uid;

    public UserProfile(){};

    public UserProfile(String Name,String Email,String Uid){
        this.Email = Email;
        this.Name = Name;
        this.Uid=Uid;
    }

}
