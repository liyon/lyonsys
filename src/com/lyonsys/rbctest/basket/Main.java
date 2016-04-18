package com.lyonsys.rbctest.basket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yong on 17/04/2016.
 */
public class Main {

    public static void main(String args[])
    {
        Basket<Fruit> basket = new Basket(initializePrices());

        System.out.println("Basket created. Now adding items to it.");

        System.out.println("Adding 2 apples to the basket");
        basket.addToBasket(Fruit.APPLE, 2);

        System.out.println("Adding 3 oranges s to the basket");
        basket.addToBasket(Fruit.ORANGE, 3);

        System.out.println("Adding 5 bananas to the basket");
        basket.addToBasket(Fruit.BANANA, 5);

        System.out.println("Adding 1 lemon s to the basket");
        basket.addToBasket(Fruit.LEMON, 1);

        System.out.println("Calculating total cost");
        System.out.println("Total cost = " + basket.getTotalCost());
    }


    private static Map<Fruit, Double> initializePrices() {
        Map<Fruit, Double> productPriceMap = new ConcurrentHashMap<>();
        System.out.println("Initializing prces for the items. Prices are as follows:");
        productPriceMap.put(Fruit.APPLE, 1.8);
        productPriceMap.put(Fruit.PEACH, 2.5);
        productPriceMap.put(Fruit.LEMON, 0.4);
        productPriceMap.put(Fruit.BANANA, 2.4);
        productPriceMap.put(Fruit.ORANGE, 3.2);
        productPriceMap.keySet().forEach(p-> System.out.println(p.getProductId() + " : " + productPriceMap.get(p)));
        return productPriceMap;
    }
}
