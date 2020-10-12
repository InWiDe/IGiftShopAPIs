package com.iGiftCards.korsitrestfulwebservices.brand;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;


//Interface that persists Product object into the database.
public interface BrandRepository extends JpaRepository<Brand, Integer> {
	
	//Filter, that searches Brand with given Name in database
	@Query("SELECT t FROM Brand t WHERE t.name = ?1")
	Optional<Brand> findByName(String Name);
}