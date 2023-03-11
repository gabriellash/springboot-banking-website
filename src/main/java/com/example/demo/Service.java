package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "service")
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	@NotNull
private String name;
	@NotNull
private String price;
	private String serviceproviderid;
	private String categoryid;
	public Service(int id, @NotNull String name, @NotNull String price, String serviceproviderid, String categoryid) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.serviceproviderid = serviceproviderid;
		this.categoryid = categoryid;
	}
	public Service() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getServiceproviderid() {
		return serviceproviderid;
	}
	public void setServiceproviderid(String serviceproviderid) {
		this.serviceproviderid = serviceproviderid;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
	
}
