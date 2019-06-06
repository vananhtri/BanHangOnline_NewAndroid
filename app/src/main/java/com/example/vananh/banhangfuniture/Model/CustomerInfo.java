package com.example.vananh.banhangfuniture.Model;

public class CustomerInfo {
    int Id;
    String Name;
    String Address;
    String PhoneNunber;
    String Email;

    public CustomerInfo() {
    }

    public CustomerInfo(int id, String name, String address, String phoneNunber, String email) {
        Id = id;
        Name = name;
        Address = address;
        PhoneNunber = phoneNunber;
        Email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNunber() {
        return PhoneNunber;
    }

    public void setPhoneNunber(String phoneNunber) {
        PhoneNunber = phoneNunber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



}
