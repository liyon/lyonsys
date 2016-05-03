package com.lyonsys.restful.spring;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PrimeNumberServiceTest {

	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

	private MockMvc mockMvc;

	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@SuppressWarnings("unchecked")
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = (HttpMessageConverter<Object>) Arrays
				.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void testGetLastPrimeNumber() throws Exception {

		testGetLastPrimeWithNumberAndExpectedResult(13, status().isOk(), 13);
		testGetLastPrimeWithNumberAndExpectedResult(12, status().isOk(), 11);
		testGetLastPrimeWithNumberAndExpectedResult(6, status().isOk(), 5);
		testGetLastPrimeWithNumberAndExpectedResult(2, status().isOk(), 2);
		testGetLastPrimeWithNumberAndExpectedResult(1, status().isNotFound(), 0);
		testGetLastPrimeWithNumberAndExpectedResult(0, status().isNotFound(), 0);
		testGetLastPrimeWithNumberAndExpectedResult(-1, status().isNotFound(),
				0);
		testGetLastPrimeWithNumberAndExpectedResult(-2, status().isNotFound(),
				0);
		testGetLastPrimeWithNumberAndExpectedResult(-13, status().isNotFound(),
				0);
	}

	private void testGetLastPrimeWithNumberAndExpectedResult(int number,
			ResultMatcher expectedStatus, int expectedResult) throws Exception {
		ResultActions action = mockMvc.perform(
				MockMvcRequestBuilders.get("/lastPrimeNumber/" + number))
				.andExpect(expectedStatus);

		if (expectedStatus.equals(status().isOk())) {
			action.andExpect(
					content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().string(String.valueOf(expectedResult)));
		}
	}

	private void testIsPrimeWithNumberAndExpectedResult(int number,
			boolean expectedResult) throws Exception {
		   mockMvc
				.perform(
						MockMvcRequestBuilders
								.get("/isPrime/" + number))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(String.valueOf(expectedResult)));
	}

	@Test
	public void testIsPrime() throws Exception {

		testIsPrimeWithNumberAndExpectedResult(5, true);
		testIsPrimeWithNumberAndExpectedResult(13, true);
		testIsPrimeWithNumberAndExpectedResult(12, false);
		
		testIsPrimeWithNumberAndExpectedResult(2, true);
		testIsPrimeWithNumberAndExpectedResult(1, false);
		testIsPrimeWithNumberAndExpectedResult(0, false);
		testIsPrimeWithNumberAndExpectedResult(-1, false);
		testIsPrimeWithNumberAndExpectedResult(-2, false);
		testIsPrimeWithNumberAndExpectedResult(-5, false);
	}

}
