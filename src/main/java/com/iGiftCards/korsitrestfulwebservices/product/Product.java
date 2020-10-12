package com.iGiftCards.korsitrestfulwebservices.product;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.iGiftCards.korsitrestfulwebservices.brand.Brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about products")
@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "Please provide a name")
	@Size(min=2, message="Name should have at least 2 characters")
	@ApiModelProperty(notes="Name should have at least 2 characters")
	private String name;

	@NotNull(message = "Please provide a value")
	private Double value;
	
	@NotNull(message = "Please provide a duration")
	private Integer duration;
	
	@NotEmpty(message = "Please provide a platform")
	private String platform;
	
	@NotEmpty(message = "Please provide a country")
	private String country;
	
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	 
	protected Product() {
		
	}
	
	public Product(Integer id,String name,Double value,Integer duration,String platform,String country,Brand brand) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.duration = duration;
		this.platform = platform;
		this.country = country;
		this.brand = brand;
	}

	// Constructor used for the testing
	public Product(String name, double value, int duration, String platform, String country) {
		super();
		this.name = name;
		this.value = value;
		this.duration = duration;
		this.platform = platform;
		this.country = country;
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


	public Double getValue() {
		return value;
	}


	public void setValue(Double value) {
		this.value = value;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
}
