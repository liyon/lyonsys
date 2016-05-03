package com.lyonsys.restful.spring;

public interface PrimeNumberCalculator {

	int NOT_FOUND = 0;
	/**
	 * Get the maximum prime number that is less than the given number.
	 * Please note this function will return 0 if there is no prime number less than the number,
	 * or if the number is less than 3 (including negative number), since by definition prime numbers are positive.
	 * @param number the number to calcualte prime number for
	 * @return The maximum prime number that is less than the given number, or 0 if no such number found
	 */
	int getLastPrimeNumber(int number);

	/**
	 * Check whether a number is prime number or not
	 * A prime number is a whole number greater than 1, whose only two whole-number factors are 1 and itself.
	 * @param number an integer
	 * @return true if the number is prime, otherwise false.
	 */
	boolean isPrime(int number);

}
