package com.lyonsys.restful.spring;

public class FunctionalPrimeNumberCalculatorTest extends PrimeNumberCalculatorTest{

	@Override
	protected PrimeNumberCalculator getCalculator() {
		return new FunctionalPrimeNumberCalculator();
	}
	
	

}
