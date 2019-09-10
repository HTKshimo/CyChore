package com.example.login;

import com.google.gson.annotations.SerializedName;

public class User
{
    private String firstName;
    private int age;
    private String mail;
    @SerializedName("address")
    private Address mAddress;
    public User(String firstName, int age, String mail, Address address)
    {
        this.firstName = firstName;
        this.age = age;
        this.mail = mail;
        mAddress = address;
    }

}
