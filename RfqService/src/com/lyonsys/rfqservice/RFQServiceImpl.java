package com.lyonsys.rfqservice;

import java.util.List;
import java.util.Optional;

/**
 * Created by yong on 16/04/2016.
 */
public class RFQServiceImpl implements RFQService {

    private static final double MARGIN = 0.02;

    private final LiveOrderBoard  liveOrderBoard;

    public RFQServiceImpl(LiveOrderBoard liveOrderBoard)
    {
        this.liveOrderBoard = liveOrderBoard;
    }

    @Override
    public Optional<Quote> quoteFor(String currency, int amount) {

        List<Order> clientOrders = liveOrderBoard.ordersFor(currency);

        if (clientOrders == null || clientOrders.isEmpty())
        {
            return Optional.empty();
        }

        Optional<Order> matchedBuyOrder = clientOrders.stream().filter(u -> u.getQuantity() == amount)
                .filter(u -> u.getSide().equals(Side.BUY))
                .max((u1, u2) -> u1.getQuantity() - u2.getQuantity());

        Optional<Order> matchedSellOrder = clientOrders.stream().filter(u -> u.getQuantity() == amount)
                .filter(u -> u.getSide().equals(Side.SELL))
                .min((u1, u2) -> u1.getQuantity() - u2.getQuantity());

        if (matchedBuyOrder.isPresent() && matchedSellOrder.isPresent())
        {
            double bid = matchedBuyOrder.get().getPrice() - MARGIN;
            double ask = matchedSellOrder.get().getPrice() + MARGIN;
            return Optional.of(new Quote(bid, ask));
        }else
        {
            return Optional.empty();
        }
    }

}
