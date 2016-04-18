package com.lyonsys.basket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Basket<T extends Product> {

    private final ConcurrentMap<T, PriceQuantity> items = new ConcurrentHashMap<>();
    private Map<T, Double> productPriceMap;

   public Basket(Map<T, Double> productPriceMap) {
        this.productPriceMap = productPriceMap;
    }


    public void addToBasket(T product, int quantity) {
        validateQuantity(quantity);
        if (this.productPriceMap == null || this.productPriceMap.get(product) == null) {
            throw new BasketException("No price found for product " + product.getProductId());
        }
        if (this.items.get(product) == null) {
            this.items.putIfAbsent(product, new PriceQuantity(productPriceMap.get(product), Integer.valueOf(0)));
        }
        PriceQuantity priceQuantity = items.get(product);
        synchronized (priceQuantity) {
            priceQuantity.setQuantity(priceQuantity.getQuantity() + quantity);
        }
    }

    public PriceQuantity removeFromBasket(Product product) {
        return this.items.remove(product);
    }

    public void updateQuantity(Product product, int newQuantity) {
        validateQuantity(newQuantity);
       this.updatePriceQuantity(product, null, newQuantity);
    }

    public void updatePrice(Product product,double price) {
        this.updatePriceQuantity(product, price, null);
    }

    public void updatePriceQuantity(Product product, Double newPrice, Integer newQuantity) {
        PriceQuantity priceQuantity = items.get(product);
        if (priceQuantity != null) {
            synchronized (priceQuantity) {
                if (newPrice != null) {
                    priceQuantity.setPrice(newPrice);
                }
                if (newQuantity != null) {
                    validateQuantity(newQuantity);
                    priceQuantity.setQuantity(newQuantity);
                }
            }
        }
    }

    private void validateQuantity(int quantity)
    {
        if (quantity <= 0 )
        {
            throw new BasketException("Quantity <" + quantity + "> is invalid");
        }
    }
    public double getTotalCost() {
        final DoubleValueHolder total = new DoubleValueHolder();

        items.keySet().stream().forEach(q -> {
            total.add(items.get(q).getPrice() * items.get(q).getQuantity());
        });
        return total.getValue();
    }

    private static class DoubleValueHolder {
        public double getValue() {
            return value;
        }

        double value;

        public void add(double delta)
        {
            value += delta;
        }
    }

}
