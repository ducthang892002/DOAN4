package com.example.doan4.Models;

public class Product {
    int Id_product,Id_danhmuc;
    String NameProduct, PictureProduct;
    int PdPrice;
    String Content, JoinDate;

    public Product(int id_product, int id_danhmuc, String nameProduct, String pictureProduct, int pdPrice, String content, String joinDate) {
        Id_product = id_product;
        Id_danhmuc = id_danhmuc;
        NameProduct = nameProduct;
        PictureProduct = pictureProduct;
        PdPrice = pdPrice;
        Content = content;
        JoinDate = joinDate;
    }

    public int getId_product() {
        return Id_product;
    }

    public void setId_product(int id_product) {
        Id_product = id_product;
    }

    public int getId_danhmuc() {
        return Id_danhmuc;
    }

    public void setId_danhmuc(int id_danhmuc) {
        Id_danhmuc = id_danhmuc;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getPictureProduct() {
        return PictureProduct;
    }

    public void setPictureProduct(String pictureProduct) {
        PictureProduct = pictureProduct;
    }

    public int getPdPrice() {
        return PdPrice;
    }

    public void setPdPrice(int pdPrice) {
        PdPrice = pdPrice;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }
}
