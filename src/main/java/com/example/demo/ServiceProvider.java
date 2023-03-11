package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "service_provider")
public class ServiceProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	@NotNull
	private String provider_name;
	private String phone_number;
	private String email;
	private String location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ServiceProvider(int id, @NotNull String provider_name, String phone_number, String email, String location) {
		super();
		this.id = id;
		this.provider_name = provider_name;
		this.phone_number = phone_number;
		this.email = email;
		this.location = location;
	}
	@Override
	public String toString() {
		return "ServiceProvider [id=" + id + ", provider_name=" + provider_name + ", phone_number=" + phone_number
				+ ", email=" + email + ", location=" + location + "]";
	}
	public ServiceProvider() {
		super();
	}
	
	
	
}
