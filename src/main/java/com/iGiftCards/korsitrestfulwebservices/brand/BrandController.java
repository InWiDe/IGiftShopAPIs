package com.iGiftCards.korsitrestfulwebservices.brand;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Validated
public class BrandController {
	@Autowired
	private final BrandRepository brandrepository;
	
	public BrandController(BrandRepository brandrepository) {
		super();
		this.brandrepository = brandrepository;		
	}
	
	 /**
	 * Use this GET request to retrieve all brands.
	 *
	 * @return GET request response with 200 status code.
	 */
	@GetMapping("/brands")
	public List<Brand> retrieveAllBrands()
	{
		return brandrepository.findAll();
	}
	
	/**
	 * Use this POST request to create new brand.
	 *
	 * @param brand is body parameter of Brand object. Validation is done regarding to this object.
	 * @return POST request response with 201 status code.
	 * @throws handleMethodArgumentNotValid with 400 status code is body parameter does not meet all validation requirements.
	 */	
	@PostMapping("/brands")
	public ResponseEntity<Object> createBrand(@Valid @RequestBody Brand brand)
	{
		Brand savedBrand = brandrepository.save(brand);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedBrand.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Use this PUT request to update brand.
	 *
	 * @param newBrand is body parameter of Brand object. Validation is done regarding to this object.
	 * @param id is the id of the brand.
	 * @return PUT request response with 200 status code.
	 * @throws handleMethodArgumentNotValid with 400 status code is body parameter does not meet all validation requirements.
	 */	
	@PutMapping("/brands/{id}")
	public Brand updateWolfPack(@RequestBody Brand newBrand, @PathVariable int id) 
	{	
		return brandrepository.findById(id).map(brand -> {
			   brand.setName(newBrand.getName());
			   return brandrepository.save(brand);
			    })
			    .orElseGet(() -> {
			    	newBrand.setId(id);
			    return brandrepository.save(newBrand);
			 });
	}	
}
