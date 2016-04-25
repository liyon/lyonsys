

package com.lyonsys.basket

import scala.collection.mutable.Map;
object Main {
  
  def main(args:Array[String])
{
      
   var basket:Basket = new Basket();
   println("Initilizing price");
   
     basket.initPrice(new Product("Orange",""), 1.2);
    basket.initPrice(new Product("Banana",""), 1.4);
   
   basket.initPrice(new Product("Orange",""), 1.8);
   basket.initPrice(new Product("Peach",""), 1.6);
   basket.initPrice(new Product("Lemon",""), 1.9);
  basket.initPrice(new Product("Apple",""), 2.6);
   
   basket.addToBasket(new Product("Banana",""), 2)
  basket.addToBasket(new Product("Orange", ""), 3)
  
    println("Total Cost = " + basket.getTotalCost());
}
}