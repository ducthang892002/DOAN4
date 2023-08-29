package com.example.doan4.Models;

public class SOrder {
    private int Id_Order,Id_Account,Id_productdetails,Quantity,TotalMoney,Status;
    private String PaymentMethod,DateTime,Notes;

    public SOrder(int id_Order, int id_Account, int id_productdetails, int quantity, int totalMoney, int status, String paymentMethod, String dateTime, String notes) {
        Id_Order = id_Order;
        Id_Account = id_Account;
        Id_productdetails = id_productdetails;
        Quantity = quantity;
        TotalMoney = totalMoney;
        Status = status;
        PaymentMethod = paymentMethod;
        DateTime = dateTime;
        Notes = notes;
    }

    public int getId_Order() {
        return Id_Order;
    }

    public void setId_Order(int id_Order) {
        Id_Order = id_Order;
    }

    public int getId_Account() {
        return Id_Account;
    }

    public void setId_Account(int id_Account) {
        Id_Account = id_Account;
    }

    public int getId_productdetails() {
        return Id_productdetails;
    }

    public void setId_productdetails(int id_productdetails) {
        Id_productdetails = id_productdetails;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        TotalMoney = totalMoney;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
