package com.lyonsys.restful.spring;

import java.util.stream.IntStream;

public class FunctionalPrimeNumberCalculator implements PrimeNumberCalculator{

	public int getLastPrimeNumber(int number) {

		if (number < 2) {
			return 0;
		} else {
			return IntStream
					.iterate(number, i -> i - 1)
					.limit(number - 1)
					.filter(i -> isPrime(i))
					.findFirst()
					.orElse(0);
		}
	}

	public boolean isPrime(int number) {

		return number == 2 ||( number >2 &&
				IntStream.rangeClosed(3,(int)Math.sqrt(number) + 1).filter( p -> p%2 !=0).allMatch(
				p -> (number % p != 0)));
	}
}
