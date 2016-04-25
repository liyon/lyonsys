package com.lyonsys.basket
import org.scalatest.FlatSpec;

class BasketTest extends FlatSpec {

  it should "Return total cost when the method is called" in
    {
      var basket: Basket = creatBasketWithPrice();

      basket.addToBasket(new Product("Banana", ""), 2)
      basket.addToBasket(new Product("Orange", ""), 3)

      assert(doubleEqual(basket.getTotalCost(), 6.4))
    }

  it should "Remove the item from basket and update total cost when removeItemFromBasket is called" in
    {
      var basket: Basket = creatBasketWithPrice();

      basket.addToBasket(new Product("Banana", ""), 2);
      basket.addToBasket(new Product("Orange", ""), 3);
      
      basket.removeFromBasket(new Product("Orange", ""))

      assert(doubleEqual(basket.getTotalCost(), 2.8))
    }

  it should "produce NoSuchElementException when getTotalCost method is invoked with no price information availble" in {
    intercept[NoSuchElementException] {
      var basket: Basket = new Basket();

      basket.addToBasket(new Product("Banana", ""), 2)
      basket.addToBasket(new Product("Orange", ""), 3)

      basket.getTotalCost();
    }
  }

  private def creatBasketWithPrice(): Basket =
    {
      var basket: Basket = new Basket();
      basket.initPrice(new Product("Orange", ""), 1.2);
      basket.initPrice(new Product("Banana", ""), 1.4);

      basket.initPrice(new Product("Peach", ""), 1.6);
      basket.initPrice(new Product("Lemon", ""), 1.9);
      basket.initPrice(new Product("Apple", ""), 2.6);
      return basket;
    }

  private def doubleEqual(d1: Double, d2: Double): Boolean =
    {
      return Math.abs(d1 - d2) < 0.00000001;
    }
}