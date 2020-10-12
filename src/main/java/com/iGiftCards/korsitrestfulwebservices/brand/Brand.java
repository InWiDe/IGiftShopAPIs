package com.iGiftCards.korsitrestfulwebservices.brand;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iGiftCards.korsitrestfulwebservices.product.Product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description="Brand of the product")
@Entity
@Table(name="brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="Brand should have the name!")
	@ApiModelProperty(notes="Brand should have the name!")
	private String name;
	
	
	@OneToMany(mappedBy = "brand")
	@JsonIgnore
    private List<Product> products;


    protected Brand()
    {
  
    }
    	
	public Brand(Integer id, @NotNull(message = "Brand should have the name!") String name, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
