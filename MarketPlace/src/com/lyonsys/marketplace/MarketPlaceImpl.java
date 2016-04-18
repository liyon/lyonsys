package com.lyonsys.marketplace;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Created by yong on 17/04/2016.
 */
public class MarketPlaceImpl implements MarketPlace {

    private ConcurrentMap<String, PriorityQueue<Bid>> bidQueueByItem = new ConcurrentHashMap<String, PriorityQueue<Bid>>();
    private ConcurrentMap<String, PriorityQueue<Offer>> offerQueueByItem = new ConcurrentHashMap<String, PriorityQueue<Offer>>();

    public List<Order> getOrders() {
        return orders;
    }

    private List<Order> orders = Collections.synchronizedList(new ArrayList<>());

    private Map<String, ReadWriteLock> lockByItem = new HashMap();

    private ReadWriteLock getLockForItem(String itemId) {
        if (lockByItem.get(itemId) == null) {
            lockByItem.putIfAbsent(itemId, new ReentrantReadWriteLock());
        }
        return lockByItem.get(itemId);
    }

    private PriorityQueue<Bid> getBidQueueIfAbsentCreate(String itemId) {
        if (bidQueueByItem.get(itemId) == null) {
            bidQueueByItem.putIfAbsent(itemId, new PriorityQueue<>(Bid.BID_PRICE_TIME_COMPARATOR));
        }
        return bidQueueByItem.get(itemId);
    }

    private PriorityQueue<Offer> getOfferQueueIfAbsentCreate(String itemId) {
        if (offerQueueByItem.get(itemId) == null) {
            offerQueueByItem.putIfAbsent(itemId, new PriorityQueue<>(Offer.OFFER_PRICE_TIME_COMPARATOR));
        }
        return offerQueueByItem.get(itemId);
    }

    @Override
    public void addBid(Bid bid) {
        bid.setQuoteTime(new Date());
        Lock lock = getLockForItem(bid.getItemId()).writeLock();
        try {
            lock.lock();
            getBidQueueIfAbsentCreate(bid.getItemId()).add(bid);
            match(bid.getItemId());
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void addOffer(Offer offer) {

        Lock lock = getLockForItem(offer.getItemId()).writeLock();
        try {
            lock.lock();
            offer.setQuoteTime(new Date());
            getOfferQueueIfAbsentCreate(offer.getItemId()).add(offer);
            match(offer.getItemId());
        } finally {
            lock.unlock();
        }
    }

    private boolean isNullOrEmpty(PriorityQueue queue) {
        return queue == null || queue.isEmpty();
    }

    private void match(String itemId) {

        PriorityQueue<Bid> bidQueue = bidQueueByItem.get(itemId);
        PriorityQueue<Offer> offerQueue = offerQueueByItem.get(itemId);

        if (isNullOrEmpty(bidQueue) ||  isNullOrEmpty(offerQueue)) {
            return;
        }
        Bid bid = bidQueue.peek();
        Offer offer = offerQueue.peek();
        if (offer.getPrice() <= bid.getPrice() && offer.getQuantity() >= bid.getQuantity()) {

            double price = Math.min(bid.getPrice(), offer.getPrice());
            orders.add(new Order(bid.getUserId(), offer.getUserId(), bid.getItemId(), price, bid.getQuantity()));
            bidQueue.remove(bid);
            if (offer.getQuantity() > bid.getQuantity()) {
                offer.setQuantity(offer.getQuantity() - bid.getQuantity());
            } else
            {
                offerQueue.remove(offer);
            }

        }

    }

    @Override
    public List<Bid> findBidByUser(String userId) {
        final List<Bid> result = new ArrayList();
        for (PriorityQueue<Bid> queue : bidQueueByItem.values()) {
            result.addAll(queue.parallelStream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public List<Offer> findOfferByUser(String userId) {

        final List<Offer> result = new ArrayList();
        for (PriorityQueue<Offer> queue : offerQueueByItem.values()) {
            result.addAll(queue.parallelStream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public double getBidPrice(String itemId) {
        Lock lock = getLockForItem(itemId).readLock();
        try {
            lock.lock();
            Bid bid = getBidQueueIfAbsentCreate(itemId).peek();
            if (bid != null) {
                return bid.getPrice();
            }

        } finally {
            lock.unlock();
        }
        return Double.NaN;
    }

    @Override
    public double getOfferPrice(String itemId) {
        Lock lock = getLockForItem(itemId).readLock();
        try {
            lock.lock();
            Offer offfer = getOfferQueueIfAbsentCreate(itemId).peek();
            if (offfer != null) {
                return offfer.getPrice();
            }

        } finally {
            lock.unlock();
        }
        return Double.NaN;
    }
}
