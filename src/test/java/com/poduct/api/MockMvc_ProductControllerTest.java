package com.poduct.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poduct.api.entity.Product;
import com.poduct.api.service.ProductService;


@SpringBootTest
@AutoConfigureMockMvc
public class MockMvc_ProductControllerTest {

@Autowired
MockMvc mockMvc;

@MockBean 
ProductService service;

private ObjectMapper objectMapper=new ObjectMapper();
@Test
@DisplayName("Test getAllProducts ok ")
public void test_getAllProducts_Ok() throws Exception
{
	Product p1=new Product(111,"hat",2.33);
	Product p2=new Product ( 333,"Pant", 32.33);
	Product p3 =new Product ( 222, "top" , 54.332);
	
	doReturn(Lists.newArrayList(p1,p2,p3)).when(service).getAllProducts();
	
	mockMvc
	.perform(
			get("/Products")
			.content(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$",hasSize(3)))
	.andExpect(jsonPath("$[0].id",	is(111)))
	.andExpect(jsonPath("$[0].title",is("hat")))
	.andExpect(jsonPath("$[0].price",is(2.33)))
	;
	
	
}

@Test
@DisplayName("Test getProductById(long id ) ok ")

public void test_getProductById_OK() throws Exception
{
	Product p3 =new Product ( 222, "top" , 54.332);

	doReturn(Optional.of(p3)).when(service).findById(222);
	
	mockMvc
	.perform(get("/Products/{id}",222L)
			.accept(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.id",is(222)))
	;
}

@Test
public void test_saveProduct() throws Exception
{
	Product p3 =new Product ( 222, "top" , 54.332);

	doReturn(p3).when(service).saveProduct(p3);
	
	mockMvc.perform(
			post("/Products")
			.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(p3))
			)
//	.andExpect(status().isCreated()) // TODO
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.id",is(222)))
	;
}

@Test
public void test_updateProduct_Ok() throws Exception
{
	Product p3 =new Product ( 222, "top" , 54.332);

	doReturn (Optional.of(p3)).when(service).findById(222);
	p3=service.findById(222).get();
	p3.setPrice(45.88);
	doReturn(p3).when(service).updateProduct(p3);
	mockMvc.perform(put("/Products")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
			.content(objectMapper.writeValueAsString(p3))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.price",is(45.88)))
			.andExpect(jsonPath("$.id",is(222)))
	
	;

}

@Test 
public void test_delete_Product() throws Exception
{
	
	mockMvc.perform(delete("/Products/{id}",222L)).andExpect(status().isOk());
}



}
