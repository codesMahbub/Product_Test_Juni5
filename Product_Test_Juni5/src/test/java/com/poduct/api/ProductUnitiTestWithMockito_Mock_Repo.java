package com.poduct.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.poduct.api.entity.Product;
import com.poduct.api.repo.ProductRepo;
import com.poduct.api.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductUnitiTestWithMockito_Mock_Repo {

	@Autowired
	ProductService service;
	@MockBean
	ProductRepo repo;

	@Test
	public void test_addProduct()
	{
		Product p =new Product(555,"Shoes Nike",55.44);
		when(repo.save(p)).thenReturn(p);
		assertEquals(p,service.saveProduct(p));
	}
	
	@Test
	public void test_GetAllProducts() {
		when(this.repo.findAll())
				.thenReturn(Stream.of(new Product(999, "Tabs", 44.44), new Product(333, "monitor", 555.55)

				).collect(Collectors.toList()));
		assertEquals(2, this.service.getAllProducts().size());
	}

	@Test
	public void test_getProdcutByid() {
		long prodId = 333;

		when(this.repo.findById(prodId)).thenReturn(Optional.of(new Product(333, "MockItem", 444.44)));
		assertNotNull(this.service.findById(prodId));
		assertEquals(true, service.findById(prodId).isPresent());
		assertEquals(333, this.service.findById(prodId).get().getId());
	}

	@Test
	public void test_deleteProduct() {
		Product p = new Product(888, "nonExistingProduct", 44.444);
		service.deleteProduct(p);
		verify(repo, times(1)).delete(p);
	}

	
	@Test
	public void test_updateProduct()
	{
		Product p = new Product(888, "nonExistingProduct", 44.444);
		p.setPrice(99.999);
		when(repo.findById(p.getId())).thenReturn(Optional.of(p));
		when(repo.save(p)).thenReturn(p);
		Product updatedP=service.updateProduct(p);
		assertEquals(99.999, updatedP.getPrice());
	}
	

@Test
public void test_deleteProduct_ById()
{
	Product p =new Product(777,"nonExistingProduct", 44.444);
	when(repo.findById(p.getId())).thenReturn(Optional.of(p));
	service.deleteProduct(p.getId());
	verify(repo,times(1)).deleteById(p.getId()); // it verifies if repo.delete(p) was called 1 times only. It does not check if the data is deleted in the db
}

}
