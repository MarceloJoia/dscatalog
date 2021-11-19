package br.com.joiamarketing.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joiamarketing.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
