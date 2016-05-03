package com.lyonsys.restful.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeNumberServiceController {

	private PrimeNumberCalculator calculator = new FunctionalPrimeNumberCalculator();

	@RequestMapping("/lastPrimeNumber/{number}")
	@ResponseBody
	public ResponseEntity<Integer> getLastPrimeNumber(@PathVariable int number) {
		int result = calculator.getLastPrimeNumber(number);
		return result == 0 ? new ResponseEntity<Integer>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	@RequestMapping("/isPrime/{number}")
	public boolean isPrime(@PathVariable int number) {
		return calculator.isPrime(number);
	}
}