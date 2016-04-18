package com.lyonsys.marketplace;

import java.util.Date;

/**
 * Created by yong on 17/04/2016.
 */
public abstract class Quote {
    public static final double DOUBLE_EQUAL_DELTA = 0.00000001;
    private final String userId;
    private final String itemId;
    private final double price;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private  int quantity;

    public Date getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Date quoteTime) {
        this.quoteTime = quoteTime;
    }

    private Date quoteTime;



    public String getUserId() {
        return userId;
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

    public Quote(String userId, String itemId, double price, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
    }

    public abstract Side getSide();

    protected static boolean pricesEqual(double price1, double price2)
    {
        return Math.abs(price1 - price2) <= DOUBLE_EQUAL_DELTA;
    }


}
