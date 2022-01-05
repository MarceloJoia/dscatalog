package br.com.joiamarketing.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.joiamarketing.dscatalog.entities.Product;
import br.com.joiamarketing.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;

	@Autowired
	private ProductRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25;
	}

	@Test
	public void findByIdShouldReturnOptionalProductWhenExistsID() {
		Optional<Product> entityProductId = repository.findById(existingId); // Action

		Assertions.assertNotNull(entityProductId.isPresent());// Assert
	}
	
	@Test
	public void findByIdShouldReturnOptionalProductWhenNonExistsID() {
			Optional<Product> entityProductId = repository.findById(nonExistingId); // Action
			Assertions.assertTrue(entityProductId.isEmpty());
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIsNull() {
		Product product = Factory.createProduct();// arrange
		product.setId(null);

		product = repository.save(product);// Action

		Assertions.assertNotNull(product.getId());// Assert
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);

		Optional<Product> entityProductId = repository.findById(existingId);

		Assertions.assertFalse(entityProductId.isPresent());
	}

	@Test
	public void deleteShouldEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
}
