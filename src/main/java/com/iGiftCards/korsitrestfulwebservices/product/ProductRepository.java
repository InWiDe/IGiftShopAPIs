package com.iGiftCards.korsitrestfulwebservices.product;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iGiftCards.korsitrestfulwebservices.brand.Brand;



//Interface that persists Product object into the database.
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	//Filter, that searches Product with given Name in database
	@Query("SELECT t FROM Product t WHERE t.name = ?1")
	Optional<Product> findByName(String Name);
	
	//Filter, that searches all Products with given Brand in database
	@Query("SELECT t FROM Product t WHERE t.brand = ?1")
	Iterable<Product> findByBrand(Brand brand);
}
