package com.lyonsys.basket;

public class Fruit extends Product {

	public static final Fruit BANANA = new Fruit("Banana", "This is a banana");
	public static final Fruit APPLE = new Fruit("Apple", "This is an apple");
	public static final Fruit ORANGE = new Fruit("Orange", "This is an orange");
	public static final Fruit LEMON = new Fruit("Lemon", "This is a lemon");
	public static final Fruit PEACH = new Fruit("Peach", "This is a Peach");
	
	// suppress the construct since all instance are available as constant
	// and other fruits are not supported.
	private Fruit(String productId, String description) {
		super(productId, description);
	}


}
