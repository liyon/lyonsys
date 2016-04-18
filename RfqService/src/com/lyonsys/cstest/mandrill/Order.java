package com.lyonsys.cstest.mandrill;

/**
 * Created by yong on 16/04/2016.
 */
public class Order {

    private Side side;
    private double price;
    private String currency;
    private int quantity;

    public Order(Side side, double price, String currency, int quantity) {
        this.side = side;
        this.price = price;
        this.currency = currency;
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public int getQuantity() {
        return quantity;
    }

}
