package com.lyonsys.cstest.mandrill;

import com.lyonsys.cstest.mandrill.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yong on 17/04/2016.
 */
public class RFQServiceImplTest {

    private RFQServiceImpl rfqService;

    private static final double DELTA_FOR_DOUBLE_EQUALITY_CHECK = 0.0000000001d;

    private final Map<String, List<Order>> testOrdersByCurrency = new HashMap<String, List<Order>>();

    @Before
    public void setUp() throws Exception {

        testOrdersByCurrency.put("USD", this.createTestOrderFor("USD"));
        TestLiveOrderBoard liveOrderBoard = new TestLiveOrderBoard(testOrdersByCurrency);
        rfqService = new RFQServiceImpl(liveOrderBoard);
    }

    @Test
    public void testQuoteForAmount() throws Exception {
        Optional<Quote> quote = rfqService.quoteFor("USD", 200);
        verifyQuote(quote, 232.69, 232.75);

        Optional<Quote> quote2 = rfqService.quoteFor("USD", 100);
        verifyQuote(quote2, 232.68, 232.76);

        Optional<Quote> quote3 = rfqService.quoteFor("USD", 300);
        assertFalse(quote3.isPresent());
    }

    @Test
    public void testQuoteWithOnlyOneSideInOrderBoard() throws Exception {

        //amount 300 only has a sell side order
        Optional<Quote> quote = rfqService.quoteFor("USD", 300);
        assertFalse(quote.isPresent());

        // now add the missing side to order board and we should have a quote
        testOrdersByCurrency.get("USD").add(new Order(Side.BUY, 232.71, "USD", 300));
        testOrdersByCurrency.get("USD").add(new Order(Side.BUY, 231.71, "USD", 300));

        quote = rfqService.quoteFor("USD", 300);
        verifyQuote(quote, 232.69, 232.78);
    }


    @Test
    public void testQuoteForNonExistingCurrency() throws Exception {
        Optional<Quote> quote = rfqService.quoteFor("GBP", 200);
        assertFalse(quote.isPresent());
    }

    @Test
    public void testNoQuoteIfOrderBoardIsEmpty() throws Exception {

        Optional<Quote> quote = rfqService.quoteFor("USD", 100);
        verifyQuote(quote, 232.68, 232.76);

        // clear order board
        testOrdersByCurrency.get("USD").clear();

        quote = rfqService.quoteFor("USD", 100);
        assertFalse(quote.isPresent());
    }

    private void verifyQuote(Optional<Quote> quote, double bid, double ask)
    {
        assertTrue(quote.isPresent());
        Quote theQuote = quote.get();
        assertEquals(bid, theQuote.getBid(), DELTA_FOR_DOUBLE_EQUALITY_CHECK);
        assertEquals(ask, theQuote.getAsk(), DELTA_FOR_DOUBLE_EQUALITY_CHECK);
    }


    private List<Order> createTestOrderFor(String currency) {
        List<Order> testOrders = new ArrayList<Order>();
        testOrders.add(new Order(Side.BUY, 232.71, currency, 200));
        testOrders.add(new Order(Side.SELL, 232.74, currency, 100));
        testOrders.add(new Order(Side.SELL, 232.73, currency, 200));
        testOrders.add(new Order(Side.BUY, 232.71, currency, 500));
        testOrders.add(new Order(Side.BUY, 232.70, currency, 100));
        testOrders.add(new Order(Side.SELL, 232.75, currency, 200));

        testOrders.add(new Order(Side.BUY, 232.69, currency, 500));
        testOrders.add(new Order(Side.SELL, 232.76, currency, 300));
        testOrders.add(new Order(Side.BUY, 232.70, currency, 200));

        return testOrders;
    }

    private static class TestLiveOrderBoard implements LiveOrderBoard {

        private final Map<String, List<Order>> testOrdersByCurrency ;


        public TestLiveOrderBoard(Map<String, List<Order>> testOrdersByCurrency )
        {
            this.testOrdersByCurrency = testOrdersByCurrency;
        }

        @Override
        public List<Order> ordersFor(String currency) {
            return testOrdersByCurrency.get(currency);
        }
    }

}