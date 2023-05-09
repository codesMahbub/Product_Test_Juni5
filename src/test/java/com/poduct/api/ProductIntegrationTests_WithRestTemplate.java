package com.poduct.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.poduct.api.entity.Product;
import com.poduct.api.repo.ProductRepo;

@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTests_WithRestTemplate {

	@LocalServerPort
	private int port;
	@Autowired
	ProductRepo productRepo;

	static RestTemplate restTemplate;

	String baseUrl = "http://localhost";
	private String context = "Products";

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setBaseurl() {
		baseUrl = baseUrl + ":" + port + "/" + context;
	}

	@Test
	public void testAddProduct() {
		System.out.println("Port : " + port);
		// add a product
		Product p = new Product(-1, "delete item", 44.33);
		ResponseEntity<Product> responseProduct = restTemplate.postForEntity(baseUrl, p, Product.class);
		assertNotNull(responseProduct);
		assertEquals(HttpStatus.OK, responseProduct.getStatusCode());

		assertEquals(responseProduct.getBody().getId(), -1);

	}

	// does not delete sometimes
	/*
	 * @Test public void testDeleteProducts() { restTemplate.delete(baseUrl +"/-1");
	 * ResponseEntity<Product[]> respList = restTemplate.getForEntity(baseUrl,
	 * Product[].class); assertNotNull(respList); assertEquals(HttpStatus.OK,
	 * respList.getStatusCode());
	 * 
	 * 
	 * assertEquals(1, respList.getBody().length);
	 * assertEquals(respList.getBody().length, productRepo.findAll().size());
	 * 
	 * }
	 */

	@Test
	public void DleteProduct_Teset_OK() {

		HttpEntity<Void> httpEntity = new HttpEntity<Void>(null, new HttpHeaders());

		ResponseEntity<Void> respEntity = restTemplate.exchange(baseUrl + "/-1", HttpMethod.DELETE, httpEntity,
				Void.class);
		assertNotNull(respEntity);

		assertEquals(HttpStatus.OK, respEntity.getStatusCode());
		System.out.println("Headers +++++++++++++++++++" + respEntity.getHeaders());
		System.out.println("httpStatus.ok=" + HttpStatus.OK.toString());

	}

	@Test
	public void testGetAllProducts() {
		ResponseEntity<Product[]> respList = restTemplate.getForEntity(baseUrl, Product[].class);

		assertNotNull(respList);
		Arrays.stream(respList.getBody()).forEach(System.out::println);
		assertEquals(HttpStatus.OK, respList.getStatusCode());
		assertEquals(respList.getBody().length, productRepo.findAll().size());

		assertEquals(1, respList.getBody().length);

	}

	@Test
	@Sql(statements = "insert into Product (id,title,price) values ( -22,'water pump',44.33)", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from Product where id =-22", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void updateProduct_OK() {
		Optional<Product> prodOptional = productRepo.findById(-22L);
		assertEquals(prodOptional.isPresent(), true);
		Product p = prodOptional.get();
		p.setPrice(99.99);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<Product> prodEntity = new HttpEntity<Product>(p, headers);

		ResponseEntity<Product> respProd = restTemplate.exchange(baseUrl, HttpMethod.PUT, prodEntity, Product.class);
		assertNotNull(respProd);
		assertEquals(respProd.getStatusCode(), HttpStatus.OK);
		assertEquals(respProd.getBody().getId(), -22L);
		assertEquals(99.99, respProd.getBody().getPrice());
	}

}
