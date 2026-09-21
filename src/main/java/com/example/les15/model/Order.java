package com.example.les15.model;


import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderid;

    private String productname;

    private double unitprice;

    private int quantity;

    public Order() { }      // default constructor required

    public Order(String prodname, double price, int quantity) {
        this.productname = prodname;
        this.unitprice = price;
        this.quantity = quantity;
    }
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateAmount() {
        return this.quantity * this.unitprice;
    }
}
