package com.example.cargo;

public class User {
    private String pib;
    private String email;
    private String address;
    private String phone;

    public User(){}
    public User(String pib, String email, String address, String phone){
        this.pib = pib;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

