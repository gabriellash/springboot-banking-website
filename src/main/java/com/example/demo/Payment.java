package com.example.demo;

import jakarta.persistence.CascadeType;
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
@Table(name = "payment")
public class Payment {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "id")
	 private int id;
	 
	 @NotNull
	 private String cardnum;
	 
	 @NotNull
	 private int cvv;
	 
	 @NotNull
	 private String type;
	 
	 @NotNull
	 private int amount;
	 
	 private boolean status = false;
	 

	 
	 @ManyToOne
	 @JoinColumn(name = "email", referencedColumnName = "email")
	  private User user;
	 
	 
	 @NotNull
	 private String bankName;
	 
	 
	 

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

	public Payment(int id, String cardnum, int cvv, String type, @NotNull int amount, boolean status, @NotNull  User user,
			@NotNull String bankName) {
		super();
		this.id = id;
		this.cardnum = cardnum;
		this.cvv = cvv;
		this.type = type;
		this.amount = amount;
		this.status = status;
		this.user = user;
		this.bankName = bankName;
	}

	public Payment() {
		super();
	}

	 
	 
}
