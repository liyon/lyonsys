package com.lyonsys.marketplace;

import java.util.Comparator;

/**
 * Created by yong on 16/04/2016.
 */
public class Offer extends Quote{

    public Offer(String userId, String itemId, double price, int quantity) {
        super(userId, itemId, price, quantity);
    }

    @Override
    public Side getSide() {
        return Side.Sell;
    }

    // Customized comparator based on price and time
    public static final Comparator<Offer> OFFER_PRICE_TIME_COMPARATOR= new Comparator<Offer>()
    {
        @Override
        public int compare(Offer o1, Offer o2) {
            if (Quote.pricesEqual(o1.getPrice(), o2.getPrice()))
            {
                // earlier offer comes first
                return o1.getQuoteTime().compareTo(o2.getQuoteTime());
            }else
            {
                // lower offer price comes first
                return o1.getPrice() < o2.getPrice()? -1: 1;
            }
        }
    };
}
