package com.lyonsys.marketplace;

import java.util.List;

/**
 * Created by yong on 16/04/2016.
 */
public interface MarketPlace {


    public void addBid(Bid bid);
    public void addOffer(Offer offer);
    public List<Bid> findBidByUser(String userId);
    public List<Offer> findOfferByUser(String userId);
    double getBidPrice(String itemId);
    double getOfferPrice(String itemId);


}



