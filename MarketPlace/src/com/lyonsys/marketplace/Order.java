package com.lyonsys.marketplace;

/**
 * Created by yong on 16/04/2016.
 */
public class Order {
    private String buyerId;



    private String sellerId;
    private String itemId;
    private double price;
    private int quantity;

    public Order(String buyerId, String sellerId, String itemId, double price, int quantity) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getItemId() {
        return itemId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
