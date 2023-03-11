package com.example.demo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "buyservice")
public class BuyService {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private int id;
	 
	 @NotNull
	 private String cost;
	 
	 @NotNull
	 private String date;
	 
	 
	 
	 @ManyToOne
	 @JoinColumn(name = "email", referencedColumnName = "email")
	  private User user;
	 
	 @NotNull
	 private String servicename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public BuyService(int id, @NotNull String cost, @NotNull String date, User user, @NotNull String servicename) {
		super();
		this.id = id;
		this.cost = cost;
		this.date = date;
		this.user = user;
		this.servicename = servicename;
	}

	public BuyService() {
		super();
	}
	 
	 

}
