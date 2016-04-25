package com.lyonsys.basket

class Product (_name: String, _desciption:String) extends Equals {
  
  def name = _name;
  
  def canEqual(other: Any) = {
    other.isInstanceOf[com.lyonsys.basket.Product]
  }

  override def equals(other: Any) = {
    other match {
      case that: com.lyonsys.basket.Product => that.canEqual(this) && that.name == this.name;
      case _ => false
    }
  }

  override def hashCode():Int = {
     31  + (if (name == null) 0  else name.hashCode);
  }
}