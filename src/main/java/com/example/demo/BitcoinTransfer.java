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
@Table(name = "bitcointransfer")
public class BitcoinTransfer {

	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "id")
	 private int id;
	 
	 @NotNull
	 private double btcamount;
	 
	 @NotNull
	 private String selleremail;
	 
	 
	 
	 private String buyeremail;
	 
	 private double usdamount;
	 
	 private double btctousd;
	 
	 private String date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBtcamount() {
		return btcamount;
	}

	public void setBtcamount(double btcamount) {
		this.btcamount = btcamount;
	}

	public String getSelleremail() {
		return selleremail;
	}

	public void setSelleremail(String selleremail) {
		this.selleremail = selleremail;
	}

	public String getBuyeremail() {
		return buyeremail;
	}

	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
	}

	public double getUsdamount() {
		return usdamount;
	}

	public void setUsdamount(double usdamount) {
		this.usdamount = usdamount;
	}

	public double getBtctousd() {
		return btctousd;
	}

	public void setBtctousd(double btctousd) {
		this.btctousd = btctousd;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BitcoinTransfer(int id, @NotNull double btcamount, @NotNull String selleremail, @NotNull String buyeremail,
			double usdamount, double btctousd, String date) {
		super();
		this.id = id;
		this.btcamount = btcamount;
		this.selleremail = selleremail;
		this.buyeremail = buyeremail;
		this.usdamount = usdamount;
		this.btctousd = btctousd;
		this.date = date;
	}

	public BitcoinTransfer() {
		super();
	}
	 
	 
}
