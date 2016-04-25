package com.lyonsys.basket

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import scala.collection._;
import scala.collection.convert.decorateAsScala._;

class Basket {

  private var items: concurrent.Map[Product, AtomicInteger] = new ConcurrentHashMap().asScala;
  private var prices = scala.collection.mutable.Map[Product, Double]();

  private val fruit = Set("Apple", "Orange", "Peach", "Banana", "Lemon");

  def initPrice(item: Product, price: Double): Unit =
    {
      prices.put(item, price);
    }

  def addToBasket(item: Product, quantity: Int) =
    {
      validateBasketItem(item);
      validateQuantty(quantity);
      if (!this.items.contains(item)) {
        this.items.putIfAbsent(item, new AtomicInteger(0));
      }
      this.items.get(item).get.getAndAdd(quantity);
    }
  
    def removeFromBasket(item: Product) =
    {
      items.remove(item);
    }

  def getTotalCost(): Double =
    {
      var totalCost: Double = 0;
      this.items.foreach(f => totalCost += prices.get(f._1).get.doubleValue() * f._2.get.doubleValue());
      return totalCost;
    }

  private def validateBasketItem(item: Product): Unit =
    {
      if (item == null || !fruit.contains(item.name)) {
        throw new RuntimeException("Invalid or unsupported product");
      }
    }
  private def validateQuantty(quantity: Int): Unit =
    {
      if (quantity <= 0) {
        throw new RuntimeException("Invalid quantity");
      }
    }

}