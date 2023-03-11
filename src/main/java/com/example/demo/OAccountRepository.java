package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OAccountRepository extends JpaRepository<OAccount, Integer> {
	@Query("Select u From OAccount u Where u.code =?1 and u.pin=?2")
	OAccount findBycp(String code, String pin);
	
	@Query(value = "Select * from OAccount u Where u.email =?1", nativeQuery = true)
	OAccount findByEmail(String semail);
	
}
