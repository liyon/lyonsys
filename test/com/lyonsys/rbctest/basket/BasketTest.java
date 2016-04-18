package com.lyonsys.rbctest.basket;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BasketTest {

    private static final double DOUBLE_EQUALLITY_DELTA = 0.0000001d;

    private Basket<Fruit> basket;
    @Before
    public void setUp() throws Exception {
        basket = new Basket<>(createPriceMap());
    }

    @Test
    public void testGetTotalCost() {

        assertEquals(0, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);

        basket.addToBasket(Fruit.APPLE, 1);
        basket.addToBasket(Fruit.LEMON, 2);
        basket.addToBasket(Fruit.PEACH, 3);

        double expectedTotalCost = 1.8 + 2* 0.4 + 3 * 2.5;
        Assert.assertEquals(expectedTotalCost, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);
    }


    @Test
    public void testAddItemToBasket() {

        initBasket();
        double originalCost = basket.getTotalCost();

        basket.addToBasket(Fruit.ORANGE, 3);
        double expected = originalCost + 3 * 3.2;
        Assert.assertEquals(expected, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);

    }


    @Test
    public void testRemoveItemFromBasket() {

        initBasket();
        double originalCost = basket.getTotalCost();

        PriceQuantity priceQuantity = basket.removeFromBasket(Fruit.LEMON);

        assertEquals(0.4, priceQuantity.getPrice(),DOUBLE_EQUALLITY_DELTA);
        assertEquals(2, priceQuantity.getQuantity().intValue());
        double  expected = originalCost - 2 * 0.4;
        Assert.assertEquals(expected, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);
    }
    @Test
    public void testUpdateQuantity() throws Exception {
        initBasket();
        double originalCost = basket.getTotalCost();
        basket.updateQuantity(Fruit.LEMON, 3); // Now has 3 lemons
        Assert.assertEquals(originalCost + 0.4, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);
    }



    @Test
    public void testUpdatePrice() throws Exception {
        initBasket();
        double originalCost = basket.getTotalCost();
        basket.updatePrice(Fruit.LEMON, 1.4); // Will increase total cost by 2 (1.4 -0.4) * 2
        Assert.assertEquals(originalCost + 2, basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);
    }

    @Test
    public void testUpdatePriceAndQuantity() throws Exception {
        initBasket();
        double originalCost = basket.getTotalCost();
        basket.updatePriceQuantity(Fruit.LEMON, 1.4, 4 ); // Will increase total cost by 1.4 *4 - 0.4 *2
        Assert.assertEquals(originalCost + 4.8 , basket.getTotalCost(), DOUBLE_EQUALLITY_DELTA);
    }

    @Test
    public void testRemoveNonExistingItemFromBasket() {

        assertNull(basket.removeFromBasket(Fruit.LEMON));
    }

    @Test (expected = BasketException.class)
    public void testAddItemToBasketWithInvalidQuantity() {
        initBasket();
        basket.addToBasket(Fruit.ORANGE, -3);
    }


    @Test (expected = BasketException.class)
    public void testUpdateQuantitytWithInvalidQuantity() {
        initBasket();
        basket.updateQuantity(Fruit.LEMON, 0);
    }

    @Test (expected = BasketException.class)
    public void testUpdatePriceAndQuantityWithInvalidQuantity() throws Exception {
        initBasket();
        basket.updatePriceQuantity(Fruit.LEMON, 1.4, -4 );
    }

    @Test (expected = BasketException.class)
    public void testAddItemWithMissingPrice() {

        Map<Fruit, Double> priceMap = createPriceMap();
        priceMap.remove(Fruit.APPLE);
        Basket<Fruit> basket = new Basket<>(priceMap);
        basket.addToBasket(Fruit.APPLE, 1);
    }

    private void initBasket()
    {
        basket.addToBasket(Fruit.APPLE, 1);
        basket.addToBasket(Fruit.LEMON, 2);
    }


    private Map<Fruit, Double> createPriceMap() {
        Map<Fruit, Double> productPriceMap = new ConcurrentHashMap<Fruit, Double>();
        productPriceMap.put(Fruit.APPLE, 1.8);
        productPriceMap.put(Fruit.PEACH, 2.5);
        productPriceMap.put(Fruit.LEMON, 0.4);
        productPriceMap.put(Fruit.BANANA, 2.4);
        productPriceMap.put(Fruit.ORANGE, 3.2);
        return productPriceMap;

    }
}
