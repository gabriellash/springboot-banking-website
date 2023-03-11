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
@Table(name = "oaccount")
public class OAccount {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	 private int id;
	 
	 @NotNull
	 private int code;
	 
	 @NotNull
	 private int pin;
	 
	 @NotNull
	 private double dollar;
	 
	 @NotNull
	 private double bitcoin;
	 
	 
	 

	 @OneToOne
	 @JoinColumn(name = "email", referencedColumnName = "email")
	  private User user;




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public int getCode() {
		return code;
	}




	public void setCode(int code) {
		this.code = code;
	}




	public int getPin() {
		return pin;
	}




	public void setPin(int pin) {
		this.pin = pin;
	}




	public double getDollar() {
		return dollar;
	}




	public void setDollar(double dollar) {
		this.dollar = dollar;
	}




	public double getBitcoin() {
		return bitcoin;
	}




	public void setBitcoin(double bitcoin) {
		this.bitcoin = bitcoin;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public OAccount(int id, @NotNull int code, @NotNull int pin, @NotNull double dollar, @NotNull double bitcoin,
			User user) {
		super();
		this.id = id;
		this.code = code;
		this.pin = pin;
		this.dollar = dollar;
		this.bitcoin = bitcoin;
		this.user = user;
	}




	public OAccount() {
		super();
	}
	 
	 
	 
	 
	 
}
