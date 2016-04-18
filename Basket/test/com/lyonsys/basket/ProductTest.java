package com.lyonsys.basket;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yong on 17/04/2016.
 */
public class ProductTest {

    @Test
    public void testEquals() throws Exception {
        Product product1 = new Product("Test", "Test Product");
        assertTrue(product1.equals(new Product("Test", "Test Product 2")));
        assertFalse(product1.equals(new Product("Test_2", "Test Product")));
        assertFalse(product1.equals(new Object()));
        assertFalse(product1.equals(null));

    }

}