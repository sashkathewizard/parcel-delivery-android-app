package com.example.cargo;

import java.util.Date;

public class PackageItem {

    //Інформація про відправника
    private String senderPib; // ПІБ
    private String senderAddress; // адреса відправника
    private String senderPhone; // телефон відправника
    //Інформація про отримувача
    private String recipientPib;
    private String recipientAddress;
    private String recipientPhone;
    //Характеристики посилки
    private String productName;
    private String weight;
    private String status;
    private String date;

    public PackageItem(){}

    /*public PackageItem(String pibSender, String addressSender, String phoneSender, String pibRecipient, String addressRecipient, String phoneRecipient, String productName, String weight, String status, String date) {
        this.pibSender = pibSender;
        this.addressSender = addressSender;
        this.phoneSender = phoneSender;
        this.pibRecipient = pibRecipient;
        this.addressRecipient = addressRecipient;
        this.phoneRecipient = phoneRecipient;

        this.productName = productName;
        this.weight = weight;
        this.status = status;
        this.date = date;
    }*/
    public PackageItem(String senderPib, String senderAddress, String senderPhone, String recipientPib, String recipientAddress, String recipientPhone, String weight, String productName, String status, String date) {
        this.senderPib = senderPib;
        this.senderAddress = senderAddress;
        this.senderPhone = senderPhone;
        this.recipientPib = recipientPib;
        this.recipientAddress = recipientAddress;
        this.recipientPhone = recipientPhone;
        this.weight = weight;
        this.productName = productName;
        this.status = status;
        this.date = date;
    }
    // Геттери та сеттери для властивості "senderPib"
    public String getSenderPib() {
        return senderPib;
    }
    public void setSenderPib(String senderPib) {
        this.senderPib = senderPib;
    }

    // Геттери та сеттери для властивості "senderAddress"
    public String getSenderAddress() {
        return senderAddress;
    }
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    // Геттери та сеттери для властивості "senderPhone"
    public String getSenderPhone() {
        return senderPhone;
    }
    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    // Геттери та сеттери для властивості "recipientPib"
    public String getRecipientPib() {
        return recipientPib;
    }
    public void setRecipientPib(String recipientPib) {
        this.recipientPib = recipientPib;
    }

    // Геттери та сеттери для властивості "recipientAddress"
    public String getRecipientAddress() {
        return recipientAddress;
    }
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    // Геттери та сеттери для властивості "recipientPhone"
    public String getRecipientPhone() {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    // Геттери та сеттери для властивості "productName"
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Геттери та сеттери для властивості "weight"
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }

    // Геттери та сеттери для властивості "status"
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Геттери та сеттери для властивості "date"
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public User getSender(){
        User sender = new User(senderPib,"none",senderAddress,senderPhone);
        return sender;
    }
    public User getRecipient(){
        User recipient = new User(recipientPib,"none",recipientAddress,recipientPhone);
        return recipient;
    }
}
