package com.iGiftCards.korsitrestfulwebservices.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iGiftCards.korsitrestfulwebservices.product.Product;
import com.iGiftCards.korsitrestfulwebservices.product.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
	
	@MockBean
	ProductRepository productrepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectmapper;
	
	
	private String exampleProductJson = "{\"name\":\"Test gift card\",\"value\":\"75.0\",\"duration\":\"2\",\"platform\":\"www.test.com\",\"country\":\"Netherlands\"}";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRetrieveAllProducts() throws Exception {
		List<Product> products = new ArrayList<>();
		products.add(new Product(55,"Uber Eats gift card",75.0,2,"https://www.ubereats.com","Netherlands",null));
		
		when(productrepository.findAll()).thenReturn(products);
		
		String url = "/products";
		
		MvcResult mvcResult = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		
		String expectedJsonResponse = objectmapper.writeValueAsString(products);
		
		JSONAssert.assertEquals(actualJsonResponse,expectedJsonResponse,false);
	}

	@Test
	public void testFindProductbyId() throws Exception {
		Product product = new Product(40,"Uber Eats gift card",75.0,2,"https://www.ubereats.com","Netherlands",null);
		Optional<Product> productOptional = Optional.of(product);
		when(productrepository.findById(anyInt())).thenReturn(productOptional);
		
		String url = "/products/getById/40";
		MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		
		String expected = objectmapper.writeValueAsString(product);

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}


	@Test
	public void testCreateProduct() throws JsonProcessingException, Exception {
		Product product = new Product(60,"Test gift card",75.0,2,"www.test.com","Netherlands",null);
		when(productrepository.save(Mockito.any(Product.class))).thenReturn(product);
		
		String url = "/products";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(url)
				.accept(MediaType.APPLICATION_JSON).content(exampleProductJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		
	}


}
