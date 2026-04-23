package com.pluralsight;

public class Product {

    private int id;

    private String name;
    private double price;
    private String unknownProductData1;
    private String unknownProductData2;
    private String unknownProductData3;
    private String unknownProductData4;


    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Product(String data1, String data2, String data3, String data4) {
        this.unknownProductData1 = data1;
        this.unknownProductData2 = data2;
        this.unknownProductData3 = data3;
        this.unknownProductData4 = data4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}