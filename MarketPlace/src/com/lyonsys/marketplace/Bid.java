package com.lyonsys.marketplace;

import java.util.Comparator;

/**
 * Created by yong on 16/04/2016.
 */
public class Bid extends Quote{


    public Bid(String userId, String itemId, double price, int quantity) {
        super(userId, itemId, price, quantity);
    }

    @Override
    public Side getSide() {
        return Side.Buy;
    }

    // Customized comparator based on price and time
    public static final Comparator<Bid> BID_PRICE_TIME_COMPARATOR= new Comparator<Bid>()
    {
        @Override
        public int compare(Bid o1, Bid o2) {
           if (Quote.pricesEqual(o1.getPrice(), o2.getPrice()))
           {
               return o1.getQuoteTime().compareTo(o2.getQuoteTime());
           }else
           {
               return o1.getPrice() > o2.getPrice()? -1: 1;
           }
        }
    };

}
