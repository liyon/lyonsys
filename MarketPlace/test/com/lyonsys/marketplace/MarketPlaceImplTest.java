package com.lyonsys.marketplace;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by yong on 17/04/2016.
 */
public class MarketPlaceImplTest {
    private static final String USER_1 = "1";
    private static final String USER_2 = "2";
    private static final String USER_3 = "3";
    private static final String USER_4 = "4";

    private static final String ITEM_1 = "12345";


    private MarketPlaceImpl marketPlace;

    private static final double DOUBLE_EQUAL_DELTA = 0.00000001;

    @Before
    public void setUp() throws Exception {

        marketPlace = new MarketPlaceImpl();
    }

    /**
     * Test the scenarios descripted in the quesion
     **/
    @Test
    public void testMarketPlace() {
        marketPlace.addBid(new Bid(USER_1, ITEM_1, 25, 10));
        marketPlace.addOffer(new Offer(USER_2, ITEM_1, 25, 5));

        List<Order> orders = marketPlace.getOrders();

        // no match
        assertTrue(orders.isEmpty());

        marketPlace.addOffer(new Offer(USER_3, ITEM_1, 24, 10));


        assertEquals(1, marketPlace.getOrders().size());
        verifyOrder(marketPlace.getOrders().get(0), USER_1, USER_3, ITEM_1, 24, 10);
        assertTrue(Double.isNaN(marketPlace.getBidPrice(ITEM_1)));
        assertEquals(25, marketPlace.getOfferPrice(ITEM_1), DOUBLE_EQUAL_DELTA);

        marketPlace.addBid(new Bid(USER_4, ITEM_1, 23, 5));
        assertEquals(1, marketPlace.getOrders().size()); // no new orders

        assertEquals(23, marketPlace.getBidPrice(ITEM_1), DOUBLE_EQUAL_DELTA);
        assertEquals(25, marketPlace.getOfferPrice(ITEM_1), DOUBLE_EQUAL_DELTA);

    }


   @Test
    public void testOfferMatchingOrder() {
        marketPlace.addOffer(new Offer(USER_2, ITEM_1, 99.00, 100));
        marketPlace.addOffer(new Offer(USER_3, ITEM_1, 98.00, 100)); // Better offer price
        marketPlace.addOffer(new Offer(USER_4, ITEM_1, 98.00, 100)); // same price but with later time stamp

       marketPlace.addBid(new Bid(USER_1, ITEM_1, 100.00, 100));

        assertEquals(1, marketPlace.getOrders().size());
        //Should match the offer with best price
        verifyOrder(marketPlace.getOrders().get(0), USER_1, USER_3, ITEM_1, 98.00, 100);

        assertEquals(1, marketPlace.findOfferByUser(USER_2).size());
        verifyBidOrOffer(marketPlace.findOfferByUser(USER_2).get(0), USER_2, Side.Sell, 99.00, 100);

        assertTrue(marketPlace.findOfferByUser(USER_3).isEmpty()); //matched

        assertEquals(1,  marketPlace.findOfferByUser(USER_4).size());
        verifyBidOrOffer(marketPlace.findOfferByUser(USER_4).get(0), USER_4, Side.Sell, 98.00, 100);

    }

    @Test
    public void testTwoBidsMatchingSameOffer() {
        marketPlace.addOffer(new Offer(USER_2, ITEM_1, 99.00, 100));
        marketPlace.addOffer(new Offer(USER_3, ITEM_1, 98.00, 220));


        marketPlace.addBid(new Bid(USER_1, ITEM_1, 100.00, 100)); //Should match USER_3' offer since the price is better

        marketPlace.addBid(new Bid(USER_1, ITEM_1, 99.00, 100)); //Should match USER_3' offer since the remaining quantity is more than bid quantity

        List<Order> orders = marketPlace.getOrders();
        assertEquals(2, orders.size());
        //Should match the offer with best price
        verifyOrder(orders.get(0), USER_1, USER_3, ITEM_1, 98.00, 100);
        verifyOrder(orders.get(1), USER_1, USER_3, ITEM_1, 98.00, 100);

        assertTrue(Double.isNaN(marketPlace.getBidPrice(ITEM_1)));
        assertEquals(98.00, marketPlace.getOfferPrice(ITEM_1), DOUBLE_EQUAL_DELTA);

        assertTrue(marketPlace.findBidByUser(USER_1).isEmpty());

        assertEquals(1, marketPlace.findOfferByUser(USER_2).size());
        verifyBidOrOffer(marketPlace.findOfferByUser(USER_2).get(0), USER_2, Side.Sell, 99.00, 100);

        assertEquals(1, marketPlace.findOfferByUser(USER_3).size());
        verifyBidOrOffer(marketPlace.findOfferByUser(USER_3).get(0), USER_3, Side.Sell, 98.00, 20);
    }

    @Test
    public void testBidMatchingOrder()
    {
        marketPlace.addBid(new Bid(USER_1, ITEM_1, 99, 100));
        marketPlace.addBid(new Bid(USER_2, ITEM_1, 100, 100));
        marketPlace.addBid(new Bid(USER_3, ITEM_1, 100, 100));

        marketPlace.addOffer(new Offer(USER_4, ITEM_1, 98.00, 100)); //  USER_2' bid should get matched since its price
        // than USER_1 and time is earlier than USER_3

        List<Order> orders = marketPlace.getOrders();
        assertEquals(1, orders.size());
        verifyOrder(orders.get(0), USER_2, USER_4, ITEM_1, 98.00, 100);

        assertEquals(100, marketPlace.getBidPrice(ITEM_1), DOUBLE_EQUAL_DELTA);
        assertTrue(Double.isNaN(marketPlace.getOfferPrice(ITEM_1)));

        List<Bid> user1Bids = marketPlace.findBidByUser(USER_1);
        assertEquals(1, user1Bids.size());
        verifyBidOrOffer(user1Bids.get(0), USER_1, Side.Buy, 99.00, 100);
        List<Bid> user2Bids = marketPlace.findBidByUser(USER_2);
        assertTrue(user2Bids.isEmpty());
        List<Bid> user3Bids = marketPlace.findBidByUser(USER_3);
        assertEquals(1, user3Bids.size());
        verifyBidOrOffer(user3Bids.get(0), USER_3, Side.Buy, 100.00, 100);
    }



    private void verifyOrder(Order order, String buyerId, String sellerId, String itemId, double price, int quantity) {
        assertEquals(buyerId, order.getBuyerId());
        assertEquals(sellerId, order.getSellerId());
        assertEquals(itemId, order.getItemId());
        assertEquals(price, order.getPrice(), DOUBLE_EQUAL_DELTA);
        assertEquals(quantity, order.getQuantity());
    }

    private void verifyBidOrOffer(Quote quote, String userId, Side side, double price, int quantiy)
    {
        assertEquals(side,quote.getSide());
        assertEquals(price ,quote.getPrice(), DOUBLE_EQUAL_DELTA);
        assertEquals(quantiy, quote.getQuantity());
        assertEquals(userId, quote.getUserId());
    }
}