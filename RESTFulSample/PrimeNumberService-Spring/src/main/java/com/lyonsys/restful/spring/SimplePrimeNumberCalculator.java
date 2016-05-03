package com.lyonsys.restful.spring;

public class SimplePrimeNumberCalculator implements PrimeNumberCalculator {

	public int getLastPrimeNumber(int number) {

		if (isInputValid(number)) {
			for (int i = number; i > 1; i--) {
				if (isPrime(i)) {
					return i;
				}
			}
		}
		return NOT_FOUND;
	}

	public boolean isPrime(int number) {
		if (!isInputValid(number)) {
			return false;
		}
		if (number == 2) {
			return true;
		}
		if (number % 2 == 0) {
			return false;
		}

		int i = 3;
		while (i < (int) Math.sqrt(number) +1) {
			if (number % i == 0) {
				return false;
			}
			i = i + 2;
		}
		return true;
	}

	private boolean isInputValid(int input) {
		return input >= 2;
	}
}
