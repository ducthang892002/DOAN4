package com.example.doan4.Models;

public class ProductDetails {
    int Id_productdetails,Id_product;
    String Size;
    int Price,Promotionalprice;
    String Picture1;
    String detail;

    public ProductDetails(int id_productdetails, int id_product, String size, int price, int promotionalprice, String picture1, String detail) {
        Id_productdetails = id_productdetails;
        Id_product = id_product;
        Size = size;
        Price = price;
        Promotionalprice = promotionalprice;
        Picture1 = picture1;
        this.detail = detail;
    }

    public int getId_productdetails() {
        return Id_productdetails;
    }

    public void setId_productdetails(int id_productdetails) {
        Id_productdetails = id_productdetails;
    }

    public int getId_product() {
        return Id_product;
    }

    public void setId_product(int id_product) {
        Id_product = id_product;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getPromotionalprice() {
        return Promotionalprice;
    }

    public void setPromotionalprice(int promotionalprice) {
        Promotionalprice = promotionalprice;
    }

    public String getPicture1() {
        return Picture1;
    }

    public void setPicture1(String picture1) {
        Picture1 = picture1;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
