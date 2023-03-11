package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BitcoinTransferRepository extends JpaRepository<BitcoinTransfer, Integer> {
	@Query(value = "Select * from BitcoinTransfer u Where u.buyeremail  IS NULL AND u.selleremail <> ?1" , nativeQuery = true)
	List<BitcoinTransfer> findByEmail( String semail);
	
	@Query(value = "Select * from BitcoinTransfer u Where u.buyeremail  IS NULL AND u.selleremail=?1 AND u.btcamount=?2" , nativeQuery = true)
	BitcoinTransfer findbyValue( String semail, double amount);
}
