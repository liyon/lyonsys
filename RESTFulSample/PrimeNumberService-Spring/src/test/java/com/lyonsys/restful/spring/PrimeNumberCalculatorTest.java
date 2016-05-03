package com.lyonsys.restful.spring;
import junit.framework.TestCase;

import org.junit.Test;


public abstract class PrimeNumberCalculatorTest extends TestCase{

	private  PrimeNumberCalculator calculator = getCalculator();
	
	
	protected abstract PrimeNumberCalculator getCalculator();

	
	@Test
	public void testGetLastPrimeNumber()
	{
		assertEquals(11, calculator.getLastPrimeNumber(12));
		assertEquals(13, calculator.getLastPrimeNumber(13));
		assertEquals(2, calculator.getLastPrimeNumber(2));
		assertEquals(0, calculator.getLastPrimeNumber(1));
		assertEquals(0, calculator.getLastPrimeNumber(-1));
		assertEquals(0, calculator.getLastPrimeNumber(-2));
		assertEquals(0, calculator.getLastPrimeNumber(-12));
	}
	
	
	@Test
	public void testIsPrime()
	{
		testPrime(2147483647, true);
		testPrime(17, true);
		testPrime(12, false);
		testPrime(5, true);
		testPrime(3, true);
		testPrime(2, true);
		testPrime(1, false);
		testPrime(0, false);
		testPrime(-1, false);
		testPrime(-2, false);
	}
	
	private void testPrime(int number, boolean expected)
	{
		assertEquals(expected,calculator.isPrime(number));
	}
}
