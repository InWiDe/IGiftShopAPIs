package com.iGiftCards.korsitrestfulwebservices.product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.iGiftCards.korsitrestfulwebservices.brand.Brand;
import com.iGiftCards.korsitrestfulwebservices.brand.BrandNotFoundException;
import com.iGiftCards.korsitrestfulwebservices.brand.BrandRepository;


@RestController
@Validated
public class ProductController {
	
	@Autowired
	private final ProductRepository productrepository;
	
	@Autowired
	private final BrandRepository brandrepository;

	public ProductController(ProductRepository productrepository,BrandRepository brandrepository) {
	        this.productrepository = productrepository;   
	        this.brandrepository = brandrepository;	
	}
	
	/**
     * Use this GET request to retrieve all products from database.
     *
     * @return Get request response with 200 status code.
     */
	@GetMapping("/products")
	public Iterable<Product> retrieveAllProducts()
	{
		return productrepository.findAll();
	}
	
	
	 /**
     * Use this GET request to retrieve product from database.
     *
     * @param Name is the Name of the product. 
     * @return GET request response with 200 status code.
     * @throws ProductNotFoundException with 404 status code if product is not present in database.
     * 
     * Also, HATEOAS has been included for navigation improvement.
     */
	@GetMapping("/products/getById/{id}")
	public EntityModel<Product> findProductbyId(@PathVariable int id)
	{
		Optional<Product> product = productrepository.findById(id);
		if(!product.isPresent())
		{
			throw new ProductNotFoundException("id:"+id);
		}
				
		//HATEOAS - Adding the control logic for consumer
		EntityModel<Product> resource = EntityModel.of(product.get());
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllProducts());
		
		resource.add(linkTo.withRel("all-products"));
		
		return resource;		
	}
	
	@GetMapping("/products/getByBrandName/{brandName}")
	public Iterable<Product> findProductsByBrandName(@PathVariable String brandName)
	{
		Optional<Brand> brandOptional = brandrepository.findByName(brandName);
		if(!brandOptional.isPresent())
		{
			throw new BrandNotFoundException("Brand:"+brandName);
		}
		Brand brand = brandOptional.get();
		
		return productrepository.findByBrand(brand);
	}
	
	
	
	/**
     * Use this POST request to create a new product.
     *
     * @param product is body parameter of Product object. Validation is done regarding to this object.
     * @return POST request response with 201 status code.
     * @throws handleMethodArgumentNotValid with 400 status code is body parameter does not meet all validation requirements.
     */
	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product)
	{
		Product savedProduct = productrepository.save(product);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedProduct.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	/**
     * Use this PUT request to update data of the product.
     * 
     * @param newProduct is body parameter of Product object. Validation is done regarding to this object.
     * @param id is the id of the specific product. Minimum value of id is 1.
     * @return PUT request response with 204 status code.
     * @throws ProductNotFoundException with 404 status code if wolf was not found in database.
     * @throws handleMethodArgumentNotValid with 400 status code is body parameter does not meet all validation requirements.
     */
	@PutMapping("/products/{id}")
	 public ResponseEntity<Object> updateProduct(@Valid @RequestBody Product newProduct, @PathVariable @Min(1) int id) {

		Optional<Product> product = productrepository.findById(id);

		if(!product.isPresent())
		{
			throw new ProductNotFoundException("id:"+id);
		}
		
		newProduct.setId(id);
	
		productrepository.save(newProduct);

		return ResponseEntity.noContent().build();
	  }
	
	/**
	 * Use this POST request to assign product to the brand.
	 *
	 * @param productId is the id of the product. Minimum value of id is 1.
	 * @param brandId is the id of the brand. Minimum value of id is 1.
	 * @return POST request response with 200 status code.
	 * @throws PackNotFoundException with 404 status code if pack is not present in database. 
	 * @throws WolfNotFoundException with 404 status code if wolf is not present in database.
	 */	
	@PostMapping("/products/{productId}/brand/{brandId}")
	public ResponseEntity<Object> AssignProductToTheBrand(@PathVariable @Min(1) int productId, @PathVariable @Min(1) int brandId)
	{
		Optional<Product> productOptional =  productrepository.findById(productId);
		if(!productOptional.isPresent())
		{
			throw new ProductNotFoundException("Product was not found: productId-"+productId);
		}
			
		Optional <Brand> brandOptional = brandrepository.findById(brandId);
		if(!brandOptional.isPresent())
		{
			throw new BrandNotFoundException("Brand was not found:brandId-"+brandId);
		}
				
		Product product = productOptional.get();
		Brand brand = brandOptional.get();
			
		product.setBrand(brand);
			
		productrepository.save(product);
					
		return ResponseEntity.ok().build();
	}
	
}
