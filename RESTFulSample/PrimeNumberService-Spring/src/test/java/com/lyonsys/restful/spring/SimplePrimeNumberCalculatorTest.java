package com.lyonsys.restful.spring;

public class SimplePrimeNumberCalculatorTest extends PrimeNumberCalculatorTest{

	@Override
	protected PrimeNumberCalculator getCalculator() {
		return new SimplePrimeNumberCalculator();
	}
	

}
